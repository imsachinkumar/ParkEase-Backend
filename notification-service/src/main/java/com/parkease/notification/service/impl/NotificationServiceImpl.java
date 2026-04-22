package com.parkease.notification.service.impl;

import com.parkease.notification.entity.Notification;
import com.parkease.notification.repository.NotificationRepository;
import com.parkease.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification sendNotification(Notification notification) {
        notification.setStatus(Notification.NotificationStatus.UNREAD);
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> getNotificationById(Integer notificationId) {
        return notificationRepository.findById(notificationId);
    }

    @Override
    public List<Notification> getNotificationsByUser(Integer userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getUnreadByUser(Integer userId) {
        return notificationRepository.findByUserIdAndStatus(
                userId, Notification.NotificationStatus.UNREAD);
    }

    @Override
    public Notification markAsRead(Integer notificationId) {
        Notification existing = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException(
                        "Notification not found: " + notificationId));
        existing.setStatus(Notification.NotificationStatus.READ);
        existing.setReadAt(LocalDateTime.now());
        return notificationRepository.save(existing);
    }

    @Override
    public void markAllAsRead(Integer userId) {
        List<Notification> unread = notificationRepository
                .findByUserIdAndStatus(userId, Notification.NotificationStatus.UNREAD);
        unread.forEach(n -> {
            n.setStatus(Notification.NotificationStatus.READ);
            n.setReadAt(LocalDateTime.now());
        });
        notificationRepository.saveAll(unread);
    }

    @Override
    public void deleteNotification(Integer notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public long countUnread(Integer userId) {
        return notificationRepository.countByUserIdAndStatus(
                userId, Notification.NotificationStatus.UNREAD);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}