package com.parkease.parkinglot.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_lots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lotId;

    @Column(nullable = false)
    private String lotName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    private String pincode;

    private Double latitude;
    private Double longitude;

    @Column(nullable = false)
    private Integer totalSpots;

    @Column(nullable = false)
    private Integer availableSpots;

    @Column(nullable = false)
    private Double pricePerHour;

    private Double evChargingPrice;

    @Column(nullable = false)
    private Boolean hasEVCharging = false;

    @Column(nullable = false)
    private Boolean isCovered = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LotStatus status = LotStatus.ACTIVE;

    private Integer ownerId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum LotStatus {
        ACTIVE, INACTIVE, MAINTENANCE
    }
}