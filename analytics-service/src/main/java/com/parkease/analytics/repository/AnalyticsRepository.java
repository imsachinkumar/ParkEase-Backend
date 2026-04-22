package com.parkease.analytics.repository;

import com.parkease.analytics.entity.AnalyticsEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<AnalyticsEvent, Integer> {

    List<AnalyticsEvent> findByEventType(AnalyticsEvent.EventType eventType);
    List<AnalyticsEvent> findByUserId(Integer userId);
    List<AnalyticsEvent> findByLotId(Integer lotId);
    List<AnalyticsEvent> findByReferenceType(AnalyticsEvent.ReferenceType referenceType);
    List<AnalyticsEvent> findByEventTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(a) FROM AnalyticsEvent a WHERE a.eventType = :eventType")
    long countByEventType(AnalyticsEvent.EventType eventType);

    @Query("SELECT COALESCE(SUM(a.amount), 0) FROM AnalyticsEvent a WHERE a.eventType = 'PAYMENT_SUCCESS'")
    Double getTotalRevenue();

    @Query("SELECT COALESCE(SUM(a.amount), 0) FROM AnalyticsEvent a WHERE a.eventType = 'PAYMENT_SUCCESS' AND a.lotId = :lotId")
    Double getRevenueByLot(Integer lotId);
}