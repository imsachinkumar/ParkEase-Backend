package com.parkease.notification.repository;

import com.parkease.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserId(Integer userId);
    List<Notification> findByUserIdAndStatus(Integer userId, Notification.NotificationStatus status);
    List<Notification> findByType(Notification.NotificationType type);
    long countByUserIdAndStatus(Integer userId, Notification.NotificationStatus status);
}