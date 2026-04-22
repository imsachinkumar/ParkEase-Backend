package com.parkease.analytics.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AnalyticsRequest {

    @NotBlank(message = "Event type is required")
    private String eventType;

    @NotNull(message = "Reference ID is required")
    private Integer referenceId;

    @NotBlank(message = "Reference type is required")
    private String referenceType;

    private Integer userId;
    private Integer lotId;
    private Integer spotId;
    private Double amount;
    private String description;
    private LocalDateTime eventTime;
}