package com.devflow.backend.controller;

import com.devflow.backend.dto.NotificationResponse;
import com.devflow.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getMyNotifications(
            Authentication authentication) {

        String userEmail = authentication.getName();

        return notificationService.getMyNotifications(
                userEmail
        );
    }

    @PatchMapping("/{notificationId}/read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markAsRead(
            @PathVariable Long notificationId,
            Authentication authentication) {

        String userEmail = authentication.getName();

        notificationService.markAsRead(
                notificationId,
                userEmail
        );
    }

    @PatchMapping("/read-all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markAllAsRead(
            Authentication authentication) {

        String userEmail = authentication.getName();

        notificationService.markAllAsRead(
                userEmail
        );
    }
}