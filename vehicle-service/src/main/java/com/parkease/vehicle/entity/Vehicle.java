package com.parkease.vehicle.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleId;

    @Column(nullable = false)
    private Integer ownerId;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    private String make;
    private String model;
    private String color;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;  // TWO_WHEELER, FOUR_WHEELER, HEAVY

    @Column(nullable = false)
    private Boolean isEV = false;

    private LocalDate registeredAt;

    @Column(nullable = false)
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        this.registeredAt = LocalDate.now();
    }

    public enum VehicleType {
        TWO_WHEELER, FOUR_WHEELER, HEAVY
    }
}