package com.parkease.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.UNREAD;

    private Integer referenceId;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.sentAt = LocalDateTime.now();
    }

    public enum NotificationType {
        BOOKING_CONFIRMED, BOOKING_CANCELLED, PAYMENT_SUCCESS,
        PAYMENT_FAILED, PAYMENT_REFUNDED, SPOT_AVAILABLE,
        GENERAL
    }

    public enum NotificationStatus {
        UNREAD, READ
    }

    public enum ReferenceType {
        BOOKING, PAYMENT, SPOT, LOT
    }
}