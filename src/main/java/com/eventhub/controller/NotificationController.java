package com.eventhub.controller;

import java.util.List;

import com.eventhub.dto.NotificationResponse;
import com.eventhub.dto.UnreadNotificationCountResponse;
import org.springframework.web.bind.annotation.*;

import com.eventhub.entity.Notification;
import com.eventhub.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public List<NotificationResponse> getNotificationsByUserId(
            @PathVariable Long userId) {

        return notificationService.getNotificationsByUserId(userId);
    }

    @PutMapping("/{id}/read")
    public String markAsRead(@PathVariable Long id) {

        notificationService.markAsRead(id);

        return "Notification marked as READ successfully";
    }

    @GetMapping("/user/{userId}/unread")
    public List<NotificationResponse> getUnreadNotifications(
            @PathVariable Long userId) {

        return notificationService.getUnreadNotifications(userId);
    }

    @GetMapping("/user/{userId}/unread-count")
    public UnreadNotificationCountResponse getUnreadNotificationCount(
            @PathVariable Long userId) {

        long count = notificationService.getUnreadNotificationCount(userId);

        return new UnreadNotificationCountResponse(count);
    }
}