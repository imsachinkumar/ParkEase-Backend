package com.parkease.auth.resource;

import com.parkease.auth.entity.User;
import com.parkease.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserResource {

    private final UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Integer userId,
            @RequestBody Map<String, String> request) {
        return userRepository.findById(userId).map(user -> {
            String status = request.get("status");
            user.setIsActive(status.equals("ACTIVE"));
            userRepository.save(user);
            return ResponseEntity.ok("Status updated");
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.ok("User deleted");
    }
}