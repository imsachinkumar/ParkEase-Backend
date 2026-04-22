package com.parkease.auth.service.impl;

import com.parkease.auth.dto.*;
import com.parkease.auth.entity.User;
import com.parkease.auth.repository.UserRepository;
import com.parkease.auth.service.AuthService;
import com.parkease.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already registered: " + req.getEmail());
        }

        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(User.Role.valueOf(req.getRole().toUpperCase()))
                .isActive(true)
                .build();

        user = userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getUserId());

        return AuthResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getIsActive()) {
            throw new RuntimeException("Account is deactivated");
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getUserId());

        return AuthResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public void logout(String token) {
        // Stateless JWT — client discards token.
        // Add Redis blacklist here if needed later.
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    @Override
    public String refreshToken(String token) {
        if (!jwtUtil.validateToken(token)) throw new RuntimeException("Invalid token");
        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return jwtUtil.generateToken(email, user.getRole().name(), user.getUserId());
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    }

    @Override
    public User updateProfile(Integer userId, Map<String, Object> updates) {
        User user = getUserById(userId);
        if (updates.containsKey("fullName")) user.setFullName((String) updates.get("fullName"));
        if (updates.containsKey("phone"))    user.setPhone((String) updates.get("phone"));
        if (updates.containsKey("profilePicUrl")) user.setProfilePicUrl((String) updates.get("profilePicUrl"));
        return userRepository.save(user);
    }

    @Override
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new RuntimeException("Old password is incorrect");
        }
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void deactivateAccount(Integer userId) {
        User user = getUserById(userId);
        user.setIsActive(false);
        userRepository.save(user);
    }
}