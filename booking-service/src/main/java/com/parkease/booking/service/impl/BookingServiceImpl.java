package com.parkease.booking.service.impl;

import com.parkease.booking.entity.Booking;
import com.parkease.booking.repository.BookingRepository;
import com.parkease.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Booking booking) {
        // Check if spot is already booked
        List<Booking> activeBookings = bookingRepository
                .findBySpotIdAndStatus(booking.getSpotId(), Booking.BookingStatus.CONFIRMED);
        if (!activeBookings.isEmpty()) {
            throw new RuntimeException("Spot is already booked: " + booking.getSpotId());
        }
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
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
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));
        existing.setStatus(Booking.BookingStatus.valueOf(status.toUpperCase()));
        return bookingRepository.save(existing);
    }

    @Override
    public Booking completeBooking(Integer bookingId, Double totalAmount) {
        Booking existing = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));
        existing.setStatus(Booking.BookingStatus.COMPLETED);
        existing.setEndTime(LocalDateTime.now());
        existing.setTotalAmount(totalAmount);
        return bookingRepository.save(existing);
    }

    @Override
    public void cancelBooking(Integer bookingId) {
        Booking existing = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));
        existing.setStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(existing);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}