package com.devflow.backend.service;

import com.devflow.backend.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getMyNotifications(
            String userEmail
    );

    void markAsRead(
            Long notificationId,
            String userEmail
    );

    void markAllAsRead(
            String userEmail
    );
}

