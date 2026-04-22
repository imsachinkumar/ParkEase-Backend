package com.parkease.booking.repository;

import com.parkease.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByUserId(Integer userId);
    List<Booking> findByVehicleId(Integer vehicleId);
    List<Booking> findByLotId(Integer lotId);
    List<Booking> findBySpotId(Integer spotId);
    List<Booking> findByStatus(Booking.BookingStatus status);
    List<Booking> findByUserIdAndStatus(Integer userId, Booking.BookingStatus status);
    List<Booking> findBySpotIdAndStatus(Integer spotId, Booking.BookingStatus status);
}