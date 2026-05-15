package com.parkease.web.controller;

import com.parkease.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/web/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final RestTemplate restTemplate;
    private static final String VEHICLE_SERVICE = "http://localhost:8086/api/v1/vehicles";

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> request) {
        try {
            Object response = restTemplate.postForObject(
                    VEHICLE_SERVICE + "/register", request, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<?> getById(@PathVariable Integer vehicleId) {
        try {
            Object response = restTemplate.getForObject(
                    VEHICLE_SERVICE + "/" + vehicleId, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getByOwner(@PathVariable Integer ownerId) {
        try {
            Object response = restTemplate.getForObject(
                    VEHICLE_SERVICE + "/owner/" + ownerId, Object.class);
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
                    VEHICLE_SERVICE + "/all", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{vehicleId}")
    public ResponseEntity<?> delete(@PathVariable Integer vehicleId) {
        try {
            restTemplate.delete(VEHICLE_SERVICE + "/delete/" + vehicleId);
            return ResponseEntity.ok(ApiResponse.success("Vehicle deleted"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}