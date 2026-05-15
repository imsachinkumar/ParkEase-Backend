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

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        try {
            Object response = restTemplate.getForObject(
                    PAYMENT_SERVICE + "/all", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/process/{paymentId}")
    public ResponseEntity<?> process(@PathVariable Integer paymentId) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(null);
            restTemplate.exchange(
                    PAYMENT_SERVICE + "/process/" + paymentId,
                    HttpMethod.PATCH, entity, Object.class);
            return ResponseEntity.ok(ApiResponse.success("Payment processed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/refund/{paymentId}")
    public ResponseEntity<?> refund(@PathVariable Integer paymentId) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(null);
            restTemplate.exchange(
                    PAYMENT_SERVICE + "/refund/" + paymentId,
                    HttpMethod.PATCH, entity, Object.class);
            return ResponseEntity.ok(ApiResponse.success("Payment refunded"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}