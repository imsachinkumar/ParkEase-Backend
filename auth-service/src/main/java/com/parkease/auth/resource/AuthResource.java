package com.parkease.auth.resource;

import com.parkease.auth.dto.*;
import com.parkease.auth.entity.User;
import com.parkease.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final AuthService authService;

    // POST /api/v1/auth/register
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // POST /api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // POST /api/v1/auth/logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        authService.logout(authHeader.replace("Bearer ", ""));
        return ResponseEntity.ok("Logged out successfully");
    }

    // GET /api/v1/auth/validate
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        return ResponseEntity.ok(authService.validateToken(token));
    }

    // POST /api/v1/auth/refresh
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(authService.refreshToken(authHeader.replace("Bearer ", "")));
    }

    // GET /api/v1/auth/profile/{userId}
    @GetMapping("/profile/{userId}")
    public ResponseEntity<User> getProfile(@PathVariable Integer userId) {
        return ResponseEntity.ok(authService.getUserById(userId));
    }

    // PUT /api/v1/auth/profile/{userId}
    @PutMapping("/profile/{userId}")
    public ResponseEntity<User> updateProfile(@PathVariable Integer userId,
                                              @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(authService.updateProfile(userId, updates));
    }

    // PUT /api/v1/auth/password/{userId}
    @PutMapping("/password/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable Integer userId,
                                                 @RequestBody Map<String, String> body) {
        authService.changePassword(userId, body.get("oldPassword"), body.get("newPassword"));
        return ResponseEntity.ok("Password changed successfully");
    }

    // DELETE /api/v1/auth/deactivate/{userId}
    @DeleteMapping("/deactivate/{userId}")
    public ResponseEntity<String> deactivateAccount(@PathVariable Integer userId) {
        authService.deactivateAccount(userId);
        return ResponseEntity.ok("Account deactivated");
    }
}