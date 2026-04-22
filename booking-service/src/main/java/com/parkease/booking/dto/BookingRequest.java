package com.parkease.booking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequest {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Vehicle ID is required")
    private Integer vehicleId;

    @NotNull(message = "Lot ID is required")
    private Integer lotId;

    @NotNull(message = "Spot ID is required")
    private Integer spotId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double totalAmount;
}