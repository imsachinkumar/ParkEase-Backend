package com.parkease.web.controller;

import com.parkease.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/web/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final RestTemplate restTemplate;
    private static final String ANALYTICS_SERVICE = "http://localhost:8088/api/v1/analytics";

    @PostMapping("/log")
    public ResponseEntity<?> log(@RequestBody Map<String, Object> request) {
        try {
            Object response = restTemplate.postForObject(
                    ANALYTICS_SERVICE + "/log", request, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/revenue/total")
    public ResponseEntity<?> getTotalRevenue() {
        try {
            Object response = restTemplate.getForObject(
                    ANALYTICS_SERVICE + "/revenue/total", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/revenue/lot/{lotId}")
    public ResponseEntity<?> getRevenueByLot(@PathVariable Integer lotId) {
        try {
            Object response = restTemplate.getForObject(
                    ANALYTICS_SERVICE + "/revenue/lot/" + lotId, Object.class);
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
                    ANALYTICS_SERVICE + "/all", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/count/{eventType}")
    public ResponseEntity<?> countByType(@PathVariable String eventType) {
        try {
            Object response = restTemplate.getForObject(
                    ANALYTICS_SERVICE + "/count/" + eventType, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}