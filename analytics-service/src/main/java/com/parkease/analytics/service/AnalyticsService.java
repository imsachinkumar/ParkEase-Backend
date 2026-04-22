package com.parkease.analytics.service;

import com.parkease.analytics.entity.AnalyticsEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnalyticsService {
    AnalyticsEvent logEvent(AnalyticsEvent event);
    Optional<AnalyticsEvent> getEventById(Integer eventId);
    List<AnalyticsEvent> getAllEvents();
    List<AnalyticsEvent> getEventsByType(String eventType);
    List<AnalyticsEvent> getEventsByUser(Integer userId);
    List<AnalyticsEvent> getEventsByLot(Integer lotId);
    List<AnalyticsEvent> getEventsByDateRange(LocalDateTime start, LocalDateTime end);
    long countEventsByType(String eventType);
    Double getTotalRevenue();
    Double getRevenueByLot(Integer lotId);
}