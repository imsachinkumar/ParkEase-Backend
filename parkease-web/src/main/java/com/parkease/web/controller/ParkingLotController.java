package com.parkease.web.controller;

import com.parkease.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/web/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final RestTemplate restTemplate;
    private static final String LOT_SERVICE = "http://localhost:8082/api/v1/parking-lots";

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> request) {
        try {
            Object response = restTemplate.postForObject(
                    LOT_SERVICE + "/create", request, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{lotId}")
    public ResponseEntity<?> getById(@PathVariable Integer lotId) {
        try {
            Object response = restTemplate.getForObject(
                    LOT_SERVICE + "/" + lotId, Object.class);
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
                    LOT_SERVICE + "/all", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<?> getByCity(@PathVariable String city) {
        try {
            Object response = restTemplate.getForObject(
                    LOT_SERVICE + "/city/" + city, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActive() {
        try {
            Object response = restTemplate.getForObject(
                    LOT_SERVICE + "/active", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}