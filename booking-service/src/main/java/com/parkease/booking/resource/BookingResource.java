package com.parkease.booking.resource;

import com.parkease.booking.dto.BookingRequest;
import com.parkease.booking.entity.Booking;
import com.parkease.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingResource {

    private final BookingService bookingService;

    // POST /api/v1/bookings/create
    @PostMapping("/create")
    public ResponseEntity<Booking> create(@Valid @RequestBody BookingRequest req) {
        Booking booking = Booking.builder()
                .userId(req.getUserId())
                .vehicleId(req.getVehicleId())
                .lotId(req.getLotId())
                .spotId(req.getSpotId())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .totalAmount(req.getTotalAmount())
                .build();
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    // GET /api/v1/bookings/{bookingId}
    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getById(@PathVariable Integer bookingId) {
        return bookingService.getBookingById(bookingId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/bookings/all
    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAll() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // GET /api/v1/bookings/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    // GET /api/v1/bookings/user/{userId}/active
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<Booking>> getActiveByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(bookingService.getActiveBookingsByUser(userId));
    }

    // GET /api/v1/bookings/vehicle/{vehicleId}
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<Booking>> getByVehicle(@PathVariable Integer vehicleId) {
        return ResponseEntity.ok(bookingService.getBookingsByVehicle(vehicleId));
    }

    // GET /api/v1/bookings/lot/{lotId}
    @GetMapping("/lot/{lotId}")
    public ResponseEntity<List<Booking>> getByLot(@PathVariable Integer lotId) {
        return ResponseEntity.ok(bookingService.getBookingsByLot(lotId));
    }

    // GET /api/v1/bookings/spot/{spotId}
    @GetMapping("/spot/{spotId}")
    public ResponseEntity<List<Booking>> getBySpot(@PathVariable Integer spotId) {
        return ResponseEntity.ok(bookingService.getBookingsBySpot(spotId));
    }

    // GET /api/v1/bookings/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    // PATCH /api/v1/bookings/status/{bookingId}
    @PatchMapping("/status/{bookingId}")
    public ResponseEntity<Booking> updateStatus(@PathVariable Integer bookingId,
                                                @RequestParam String status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(bookingId, status));
    }

    // PATCH /api/v1/bookings/complete/{bookingId}
    @PatchMapping("/complete/{bookingId}")
    public ResponseEntity<Booking> complete(@PathVariable Integer bookingId,
                                            @RequestParam Double totalAmount) {
        return ResponseEntity.ok(bookingService.completeBooking(bookingId, totalAmount));
    }

    // PATCH /api/v1/bookings/cancel/{bookingId}
    @PatchMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancel(@PathVariable Integer bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled successfully");
    }
}