package com.parkease.spot.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SpotRequest {

    @NotNull(message = "Lot ID is required")
    private Integer lotId;

    @NotBlank(message = "Spot number is required")
    private String spotNumber;

    @NotBlank(message = "Spot type is required: TWO_WHEELER, FOUR_WHEELER, HEAVY, EV")
    private String spotType;

    private Boolean isEV = false;
    private Boolean isCovered = false;
}