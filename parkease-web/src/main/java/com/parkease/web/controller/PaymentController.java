package com.parkease.web.controller;

import com.parkease.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/web/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final RestTemplate restTemplate;
    private static final String PAYMENT_SERVICE = "http://localhost:8085/api/v1/payments";

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> request) {
        try {
            Object response = restTemplate.postForObject(
                    PAYMENT_SERVICE + "/create", request, Object.class);
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
                    PAYMENT_SERVICE + "/user/" + userId, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getByBooking(@PathVariable Integer bookingId) {
        try {
            Object response = restTemplate.getForObject(
                    PAYMENT_SERVICE + "/booking/" + bookingId, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PatchMapping("/process/{paymentId}")
    public ResponseEntity<?> process(@PathVariable Integer paymentId) {
        try {
            Object response = restTemplate.patchForObject(
                    PAYMENT_SERVICE + "/process/" + paymentId, null, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PatchMapping("/refund/{paymentId}")
    public ResponseEntity<?> refund(@PathVariable Integer paymentId) {
        try {
            Object response = restTemplate.patchForObject(
                    PAYMENT_SERVICE + "/refund/" + paymentId, null, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}