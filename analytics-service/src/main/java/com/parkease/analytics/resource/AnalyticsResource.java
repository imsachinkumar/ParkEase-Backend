package com.parkease.analytics.resource;

import com.parkease.analytics.dto.AnalyticsRequest;
import com.parkease.analytics.entity.AnalyticsEvent;
import com.parkease.analytics.service.AnalyticsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsResource {

    private final AnalyticsService analyticsService;

    // POST /api/v1/analytics/log
    @PostMapping("/log")
    public ResponseEntity<AnalyticsEvent> log(@Valid @RequestBody AnalyticsRequest req) {
        AnalyticsEvent event = AnalyticsEvent.builder()
                .eventType(AnalyticsEvent.EventType.valueOf(req.getEventType().toUpperCase()))
                .referenceId(req.getReferenceId())
                .referenceType(AnalyticsEvent.ReferenceType.valueOf(
                        req.getReferenceType().toUpperCase()))
                .userId(req.getUserId())
                .lotId(req.getLotId())
                .spotId(req.getSpotId())
                .amount(req.getAmount())
                .description(req.getDescription())
                .eventTime(req.getEventTime())
                .build();
        return ResponseEntity.ok(analyticsService.logEvent(event));
    }

    // GET /api/v1/analytics/{eventId}
    @GetMapping("/{eventId}")
    public ResponseEntity<AnalyticsEvent> getById(@PathVariable Integer eventId) {
        return analyticsService.getEventById(eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/analytics/all
    @GetMapping("/all")
    public ResponseEntity<List<AnalyticsEvent>> getAll() {
        return ResponseEntity.ok(analyticsService.getAllEvents());
    }

    // GET /api/v1/analytics/type/{eventType}
    @GetMapping("/type/{eventType}")
    public ResponseEntity<List<AnalyticsEvent>> getByType(@PathVariable String eventType) {
        return ResponseEntity.ok(analyticsService.getEventsByType(eventType));
    }

    // GET /api/v1/analytics/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AnalyticsEvent>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(analyticsService.getEventsByUser(userId));
    }

    // GET /api/v1/analytics/lot/{lotId}
    @GetMapping("/lot/{lotId}")
    public ResponseEntity<List<AnalyticsEvent>> getByLot(@PathVariable Integer lotId) {
        return ResponseEntity.ok(analyticsService.getEventsByLot(lotId));
    }

    // GET /api/v1/analytics/revenue/total
    @GetMapping("/revenue/total")
    public ResponseEntity<Double> getTotalRevenue() {
        return ResponseEntity.ok(analyticsService.getTotalRevenue());
    }

    // GET /api/v1/analytics/revenue/lot/{lotId}
    @GetMapping("/revenue/lot/{lotId}")
    public ResponseEntity<Double> getRevenueByLot(@PathVariable Integer lotId) {
        return ResponseEntity.ok(analyticsService.getRevenueByLot(lotId));
    }

    // GET /api/v1/analytics/count/{eventType}
    @GetMapping("/count/{eventType}")
    public ResponseEntity<Long> countByType(@PathVariable String eventType) {
        return ResponseEntity.ok(analyticsService.countEventsByType(eventType));
    }

    // GET /api/v1/analytics/range?start=...&end=...
    @GetMapping("/range")
    public ResponseEntity<List<AnalyticsEvent>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(analyticsService.getEventsByDateRange(start, end));
    }
}