package com.parkease.parkinglot.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ParkingLotRequest {

    @NotBlank(message = "Lot name is required")
    private String lotName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    private String pincode;

    private Double latitude;
    private Double longitude;

    @NotNull(message = "Total spots required")
    @Min(value = 1, message = "Minimum 1 spot required")
    private Integer totalSpots;

    @NotNull(message = "Price per hour required")
    private Double pricePerHour;

    private Double evChargingPrice;

    private Boolean hasEVCharging = false;
    private Boolean isCovered = false;

    private Integer ownerId;
}