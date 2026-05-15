package com.parkease.booking.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private Integer userId;
    private String title;
    private String message;
    private String type;
    private Integer referenceId;
    private String referenceType;
}