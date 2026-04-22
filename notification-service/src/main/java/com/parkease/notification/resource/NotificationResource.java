package com.parkease.notification.resource;

import com.parkease.notification.dto.NotificationRequest;
import com.parkease.notification.entity.Notification;
import com.parkease.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationResource {

    private final NotificationService notificationService;

    // POST /api/v1/notifications/send
    @PostMapping("/send")
    public ResponseEntity<Notification> send(@Valid @RequestBody NotificationRequest req) {
        Notification notification = Notification.builder()
                .userId(req.getUserId())
                .title(req.getTitle())
                .message(req.getMessage())
                .type(Notification.NotificationType.valueOf(req.getType().toUpperCase()))
                .referenceId(req.getReferenceId())
                .referenceType(req.getReferenceType() != null ?
                        Notification.ReferenceType.valueOf(req.getReferenceType().toUpperCase())
                        : null)
                .build();
        return ResponseEntity.ok(notificationService.sendNotification(notification));
    }

    // GET /api/v1/notifications/{notificationId}
    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> getById(@PathVariable Integer notificationId) {
        return notificationService.getNotificationById(notificationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/notifications/all
    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    // GET /api/v1/notifications/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
    }

    // GET /api/v1/notifications/user/{userId}/unread
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnread(@PathVariable Integer userId) {
        return ResponseEntity.ok(notificationService.getUnreadByUser(userId));
    }

    // GET /api/v1/notifications/user/{userId}/count
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> countUnread(@PathVariable Integer userId) {
        return ResponseEntity.ok(notificationService.countUnread(userId));
    }

    // PATCH /api/v1/notifications/read/{notificationId}
    @PatchMapping("/read/{notificationId}")
    public ResponseEntity<Notification> markAsRead(@PathVariable Integer notificationId) {
        return ResponseEntity.ok(notificationService.markAsRead(notificationId));
    }

    // PATCH /api/v1/notifications/read/all/{userId}
    @PatchMapping("/read/all/{userId}")
    public ResponseEntity<String> markAllAsRead(@PathVariable Integer userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok("All notifications marked as read");
    }

    // DELETE /api/v1/notifications/delete/{notificationId}
    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<String> delete(@PathVariable Integer notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}