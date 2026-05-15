package com.parkease.web.controller;

import com.parkease.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/web/users")
@RequiredArgsConstructor
public class UserController {

    private final RestTemplate restTemplate;
    private static final String AUTH_SERVICE = "http://localhost:8081/api/v1/users";

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            Object response = restTemplate.getForObject(
                    AUTH_SERVICE + "/all", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Integer userId,
            @RequestBody Map<String, String> request) {
        try {
            restTemplate.put(
                    AUTH_SERVICE + "/" + userId + "/status", request);
            return ResponseEntity.ok(ApiResponse.success("Status updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        try {
            restTemplate.delete(AUTH_SERVICE + "/delete/" + userId);
            return ResponseEntity.ok(ApiResponse.success("User deleted"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}