package com.parkease.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(nullable = false)
    private Integer referenceId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    private Integer userId;
    private Integer lotId;
    private Integer spotId;
    private Double amount;

    private String description;

    private LocalDateTime eventTime;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.eventTime == null) {
            this.eventTime = LocalDateTime.now();
        }
    }

    public enum EventType {
        BOOKING_CREATED, BOOKING_CANCELLED, BOOKING_COMPLETED,
        PAYMENT_SUCCESS, PAYMENT_FAILED, PAYMENT_REFUNDED,
        SPOT_OCCUPIED, SPOT_RELEASED, USER_REGISTERED,
        VEHICLE_REGISTERED
    }

    public enum ReferenceType {
        BOOKING, PAYMENT, SPOT, LOT, USER, VEHICLE
    }
}