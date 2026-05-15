package com.parkease.booking.service.impl;

import com.parkease.booking.entity.Booking;
import com.parkease.booking.event.NotificationEvent;
import com.parkease.booking.repository.BookingRepository;
import com.parkease.booking.service.BookingService;
import com.parkease.booking.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RabbitTemplate rabbitTemplate;

    private void sendNotification(Integer userId, String title,
                                  String message, String type,
                                  Integer referenceId) {
        try {
            NotificationEvent event = NotificationEvent.builder()
                    .userId(userId)
                    .title(title)
                    .message(message)
                    .type(type)
                    .referenceId(referenceId)
                    .referenceType("BOOKING")
                    .build();

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.NOTIFICATION_EXCHANGE,
                    RabbitMQConfig.NOTIFICATION_ROUTING_KEY,
                    event
            );
            System.out.println("✅ Notification sent to RabbitMQ: " + title);
        } catch (Exception e) {
            System.out.println("❌ RabbitMQ failed: " + e.getMessage());
        }
    }

    @Override
    public Booking createBooking(Booking booking) {
        if (booking.getSpotId() != null) {
            List<Booking> activeBookings = bookingRepository
                    .findBySpotIdAndStatus(booking.getSpotId(),
                            Booking.BookingStatus.CONFIRMED);
            if (!activeBookings.isEmpty()) {
                throw new RuntimeException("Spot is already booked: "
                        + booking.getSpotId());
            }
        }
        booking.setStatus(Booking.BookingStatus.PENDING);
        Booking saved = bookingRepository.save(booking);

        sendNotification(
                saved.getUserId(),
                "Booking Created! 🅿️",
                "Your booking #" + saved.getBookingId() +
                        " has been created successfully!",
                "BOOKING_CONFIRMED",
                saved.getBookingId()
        );

        return saved;
    }

    @Override
    public Optional<Booking> getBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Override
    public List<Booking> getBookingsByUser(Integer userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public List<Booking> getBookingsByVehicle(Integer vehicleId) {
        return bookingRepository.findByVehicleId(vehicleId);
    }

    @Override
    public List<Booking> getBookingsByLot(Integer lotId) {
        return bookingRepository.findByLotId(lotId);
    }

    @Override
    public List<Booking> getBookingsBySpot(Integer spotId) {
        return bookingRepository.findBySpotId(spotId);
    }

    @Override
    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(
                Booking.BookingStatus.valueOf(status.toUpperCase()));
    }

    @Override
    public List<Booking> getActiveBookingsByUser(Integer userId) {
        return bookingRepository.findByUserIdAndStatus(
                userId, Booking.BookingStatus.CONFIRMED);
    }

    @Override
    public Booking updateBookingStatus(Integer bookingId, String status) {
        Booking existing = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException(
                        "Booking not found: " + bookingId));
        existing.setStatus(Booking.BookingStatus.valueOf(status.toUpperCase()));
        Booking saved = bookingRepository.save(existing);

        sendNotification(
                saved.getUserId(),
                "Booking Status Updated! 📋",
                "Your booking #" + bookingId + " status is now: " + status,
                "GENERAL",
                bookingId
        );

        return saved;
    }

    @Override
    public Booking completeBooking(Integer bookingId, Double totalAmount) {
        Booking existing = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException(
                        "Booking not found: " + bookingId));
        existing.setStatus(Booking.BookingStatus.COMPLETED);
        existing.setEndTime(LocalDateTime.now());
        existing.setTotalAmount(totalAmount);
        Booking saved = bookingRepository.save(existing);

        sendNotification(
                saved.getUserId(),
                "Checkout Successful! 🚗",
                "Your parking session at Lot #" + saved.getLotId() +
                        " is complete. Thank you for using ParkEase!",
                "BOOKING_CANCELLED",
                bookingId
        );

        return saved;
    }

    @Override
    public void cancelBooking(Integer bookingId) {
        Booking existing = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException(
                        "Booking not found: " + bookingId));
        existing.setStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(existing);

        sendNotification(
                existing.getUserId(),
                "Booking Cancelled ❌",
                "Your booking #" + bookingId + " has been cancelled.",
                "BOOKING_CANCELLED",
                bookingId
        );
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}