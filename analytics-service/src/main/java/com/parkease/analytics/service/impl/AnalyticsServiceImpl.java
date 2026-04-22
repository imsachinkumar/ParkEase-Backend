package com.parkease.analytics.service.impl;

import com.parkease.analytics.entity.AnalyticsEvent;
import com.parkease.analytics.repository.AnalyticsRepository;
import com.parkease.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    @Override
    public AnalyticsEvent logEvent(AnalyticsEvent event) {
        return analyticsRepository.save(event);
    }

    @Override
    public Optional<AnalyticsEvent> getEventById(Integer eventId) {
        return analyticsRepository.findById(eventId);
    }

    @Override
    public List<AnalyticsEvent> getAllEvents() {
        return analyticsRepository.findAll();
    }

    @Override
    public List<AnalyticsEvent> getEventsByType(String eventType) {
        return analyticsRepository.findByEventType(
                AnalyticsEvent.EventType.valueOf(eventType.toUpperCase()));
    }

    @Override
    public List<AnalyticsEvent> getEventsByUser(Integer userId) {
        return analyticsRepository.findByUserId(userId);
    }

    @Override
    public List<AnalyticsEvent> getEventsByLot(Integer lotId) {
        return analyticsRepository.findByLotId(lotId);
    }

    @Override
    public List<AnalyticsEvent> getEventsByDateRange(LocalDateTime start, LocalDateTime end) {
        return analyticsRepository.findByEventTimeBetween(start, end);
    }

    @Override
    public long countEventsByType(String eventType) {
        return analyticsRepository.countByEventType(
                AnalyticsEvent.EventType.valueOf(eventType.toUpperCase()));
    }

    @Override
    public Double getTotalRevenue() {
        return analyticsRepository.getTotalRevenue();
    }

    @Override
    public Double getRevenueByLot(Integer lotId) {
        return analyticsRepository.getRevenueByLot(lotId);
    }
}