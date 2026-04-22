package com.parkease.web.controller;

import com.parkease.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/web/spots")
@RequiredArgsConstructor
public class SpotController {

    private final RestTemplate restTemplate;
    private static final String SPOT_SERVICE = "http://localhost:8083/api/v1/spots";

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> request) {
        try {
            Object response = restTemplate.postForObject(
                    SPOT_SERVICE + "/create", request, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/lot/{lotId}")
    public ResponseEntity<?> getByLot(@PathVariable Integer lotId) {
        try {
            Object response = restTemplate.getForObject(
                    SPOT_SERVICE + "/lot/" + lotId, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/lot/{lotId}/available")
    public ResponseEntity<?> getAvailable(@PathVariable Integer lotId) {
        try {
            Object response = restTemplate.getForObject(
                    SPOT_SERVICE + "/lot/" + lotId + "/available", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/lot/{lotId}/available/type/{spotType}")
    public ResponseEntity<?> getAvailableByType(@PathVariable Integer lotId,
                                                @PathVariable String spotType) {
        try {
            Object response = restTemplate.getForObject(
                    SPOT_SERVICE + "/lot/" + lotId + "/available/type/" + spotType,
                    Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}