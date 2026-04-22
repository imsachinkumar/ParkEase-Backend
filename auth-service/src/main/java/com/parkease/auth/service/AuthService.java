package com.parkease.auth.service;

import com.parkease.auth.dto.*;
import com.parkease.auth.entity.User;
import java.util.Map;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void logout(String token);
    boolean validateToken(String token);
    String refreshToken(String token);
    User getUserByEmail(String email);
    User getUserById(Integer userId);
    User updateProfile(Integer userId, Map<String, Object> updates);
    void changePassword(Integer userId, String oldPassword, String newPassword);
    void deactivateAccount(Integer userId);
}