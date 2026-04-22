package com.parkease.web.controller;

import com.parkease.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/web/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final RestTemplate restTemplate;
    private static final String NOTIFICATION_SERVICE =
            "http://localhost:8087/api/v1/notifications";

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody Map<String, Object> request) {
        try {
            Object response = restTemplate.postForObject(
                    NOTIFICATION_SERVICE + "/send", request, Object.class);
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
                    NOTIFICATION_SERVICE + "/user/" + userId, Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<?> getUnread(@PathVariable Integer userId) {
        try {
            Object response = restTemplate.getForObject(
                    NOTIFICATION_SERVICE + "/user/" + userId + "/unread", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<?> countUnread(@PathVariable Integer userId) {
        try {
            Object response = restTemplate.getForObject(
                    NOTIFICATION_SERVICE + "/user/" + userId + "/count", Object.class);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}