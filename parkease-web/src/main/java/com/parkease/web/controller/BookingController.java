package com.parkease.web.controller;

import com.parkease.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/web/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final RestTemplate restTemplate;
    private static final String BOOKING_SERVICE = "http://localhost:8084/api/v1/bookings";

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> request) {
        try {
            Object response = restTemplate.postForObject(
                    BOOKING_SERVICE + "/create", request, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getById(@PathVariable Integer bookingId) {
        try {
            Object response = restTemplate.getForObject(
                    BOOKING_SERVICE + "/" + bookingId, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable Integer userId) {
        try {
            Object response = restTemplate.getForObject(
                    BOOKING_SERVICE + "/user/" + userId, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<?> getActiveByUser(@PathVariable Integer userId) {
        try {
            Object response = restTemplate.getForObject(
                    BOOKING_SERVICE + "/user/" + userId + "/active", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PatchMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancel(@PathVariable Integer bookingId) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(null);
            restTemplate.exchange(
                    BOOKING_SERVICE + "/cancel/" + bookingId,
                    HttpMethod.PATCH, entity, Object.class);
            return ResponseEntity.ok(ApiResponse.success("Booking cancelled"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        try {
            Object response = restTemplate.getForObject(
                    BOOKING_SERVICE + "/all", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Integer bookingId,
            @RequestBody Map<String, Object> request) {
        try {
            String status = request.get("status").toString();
            HttpEntity<Void> entity = new HttpEntity<>(null);
            restTemplate.exchange(
                    BOOKING_SERVICE + "/status/" + bookingId + "?status=" + status,
                    HttpMethod.PATCH, entity, Object.class);
            return ResponseEntity.ok(ApiResponse.success("Status updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PatchMapping("/checkin/{bookingId}")
    public ResponseEntity<?> checkIn(@PathVariable Integer bookingId) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(null);
            restTemplate.exchange(
                    BOOKING_SERVICE + "/status/" + bookingId + "?status=ACTIVE",
                    HttpMethod.PATCH, entity, Object.class);
            return ResponseEntity.ok(ApiResponse.success("Checked in successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PatchMapping("/checkout/{bookingId}")
    public ResponseEntity<?> checkOut(@PathVariable Integer bookingId) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(null);
            restTemplate.exchange(
                    BOOKING_SERVICE + "/complete/" + bookingId + "?totalAmount=0",
                    HttpMethod.PATCH, entity, Object.class);
            return ResponseEntity.ok(ApiResponse.success("Checked out successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}