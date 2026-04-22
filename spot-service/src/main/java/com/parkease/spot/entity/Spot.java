package com.parkease.spot.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "spots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spotId;

    @Column(nullable = false)
    private Integer lotId;

    @Column(nullable = false)
    private String spotNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SpotType spotType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SpotStatus status = SpotStatus.AVAILABLE;

    @Column(nullable = false)
    private Boolean isEV = false;

    private Boolean isCovered = false;

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

    public enum SpotType {
        TWO_WHEELER, FOUR_WHEELER, HEAVY, EV
    }

    public enum SpotStatus {
        AVAILABLE, OCCUPIED, RESERVED, MAINTENANCE
    }
}