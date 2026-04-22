package com.parkease.vehicle.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VehicleRequest {

    @NotNull
    private Integer ownerId;

    @NotBlank(message = "License plate is required")
    private String licensePlate;

    private String make;
    private String model;
    private String color;

    @NotBlank(message = "Vehicle type is required: TWO_WHEELER, FOUR_WHEELER, HEAVY")
    private String vehicleType;

    private Boolean isEV = false;
}