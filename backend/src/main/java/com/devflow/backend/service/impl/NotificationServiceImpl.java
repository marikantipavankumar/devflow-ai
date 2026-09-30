package com.devflow.backend.service.impl;

import com.devflow.backend.dto.NotificationResponse;
import com.devflow.backend.entity.Notification;
import com.devflow.backend.entity.User;
import com.devflow.backend.exception.AccessDeniedException;
import com.devflow.backend.exception.ResourceNotFoundException;
import com.devflow.backend.repository.NotificationRepository;
import com.devflow.backend.repository.UserRepository;
import com.devflow.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public List<NotificationResponse> getMyNotifications(
            String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail
                        )
                );

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(notification -> new NotificationResponse(
                        notification.getId(),
                        notification.getMessage(),
                        notification.isRead(),
                        notification.getCreatedAt()
                ))
                .toList();
    }

    @Override
    public void markAsRead(
            Long notificationId,
            String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail
                        )
                );

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found with ID: "
                                                + notificationId
                                )
                        );

        if (!notification.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(
                    "You are not authorized to update this notification"
            );
        }

        notification.setRead(true);

        notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead(
            String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail
                        )
                );

        List<Notification> notifications =
                notificationRepository
                        .findByUserOrderByCreatedAtDesc(user);

        notifications.forEach(notification ->
                notification.setRead(true)
        );

        notificationRepository.saveAll(notifications);
    }
}