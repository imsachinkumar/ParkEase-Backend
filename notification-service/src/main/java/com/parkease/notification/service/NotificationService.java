package com.parkease.notification.service;

import com.parkease.notification.entity.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationService {
    Notification sendNotification(Notification notification);
    Optional<Notification> getNotificationById(Integer notificationId);
    List<Notification> getNotificationsByUser(Integer userId);
    List<Notification> getUnreadByUser(Integer userId);
    Notification markAsRead(Integer notificationId);
    void markAllAsRead(Integer userId);
    void deleteNotification(Integer notificationId);
    long countUnread(Integer userId);
    List<Notification> getAllNotifications();
}