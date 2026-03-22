package org.example.backend.service;

import org.example.backend.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    void createNotification(Long userId, String message, String type);
    List<NotificationDTO> getNotificationsForUser(Long userId);
    void markAsRead(Long notificationId);
}