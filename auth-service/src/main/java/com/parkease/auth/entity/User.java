package com.parkease.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String passwordHash;   // null for OAuth users

    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;             // DRIVER, MANAGER, ADMIN

    private String vehiclePlate;   // quick ref for single-vehicle accounts

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String profilePicUrl;

    private String oauthProvider;  // "google", "github", or null
    private String oauthId;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public enum Role {
        DRIVER, MANAGER, ADMIN
    }
}