package com.parkease.booking.service;

import com.parkease.booking.entity.Booking;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createBooking(Booking booking);
    Optional<Booking> getBookingById(Integer bookingId);
    List<Booking> getBookingsByUser(Integer userId);
    List<Booking> getBookingsByVehicle(Integer vehicleId);
    List<Booking> getBookingsByLot(Integer lotId);
    List<Booking> getBookingsBySpot(Integer spotId);
    List<Booking> getBookingsByStatus(String status);
    List<Booking> getActiveBookingsByUser(Integer userId);
    Booking updateBookingStatus(Integer bookingId, String status);
    Booking completeBooking(Integer bookingId, Double totalAmount);
    void cancelBooking(Integer bookingId);
    List<Booking> getAllBookings();
}