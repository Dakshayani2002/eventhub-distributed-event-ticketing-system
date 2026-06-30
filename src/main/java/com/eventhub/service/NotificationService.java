package com.eventhub.service;

import java.time.LocalDateTime;
import java.util.List;

import com.eventhub.dto.NotificationResponse;
import com.eventhub.kafka.Dto.BookingEvent;
import com.eventhub.kafka.Dto.PaymentEvent;
import org.springframework.stereotype.Service;
import com.eventhub.entity.Notification;
import com.eventhub.enums.NotificationStatus;
import com.eventhub.enums.NotificationType;
import com.eventhub.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void createBookingNotification(BookingEvent event) {

        Notification notification = Notification.builder()
                .userId(event.getUserId())
                .message("Your booking has been confirmed. Booking ID: " + event.getBookingId())
                .notificationType(NotificationType.BOOKING)
                .status(NotificationStatus.UNREAD)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        log.info("Notification saved successfully for bookingId: {}", event.getBookingId());
    }

    public List<NotificationResponse> getNotificationsByUserId(Long userId) {

        log.info("Fetching notifications for userId: {}", userId);

        List<Notification> notifications = notificationRepository.findByUserId(userId);

        return notifications.stream()
                .map(notification -> NotificationResponse.builder()
                        .id(notification.getId())
                        .message(notification.getMessage())
                        .notificationType(notification.getNotificationType())
                        .status(notification.getStatus())
                        .createdAt(notification.getCreatedAt())
                        .build())
                .toList();
    }

    public void markAsRead(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));

        notification.setStatus(NotificationStatus.READ);

        notificationRepository.save(notification);

        log.info("Notification marked as READ for id: {}", notificationId);
    }

    public List<NotificationResponse> getUnreadNotifications(Long userId) {

        log.info("Fetching unread notifications for userId: {}", userId);

        List<Notification> notifications =
                notificationRepository.findByUserIdAndStatus(
                        userId,
                        NotificationStatus.UNREAD);

        return notifications.stream()
                .map(notification -> NotificationResponse.builder()
                        .id(notification.getId())
                        .message(notification.getMessage())
                        .notificationType(notification.getNotificationType())
                        .status(notification.getStatus())
                        .createdAt(notification.getCreatedAt())
                        .build())
                .toList();
    }

    public long getUnreadNotificationCount(Long userId) {

        log.info("Fetching unread notification count for userId: {}", userId);

        return notificationRepository.countByUserIdAndStatus(
                userId,
                NotificationStatus.UNREAD);
    }

    public void createPaymentNotification(PaymentEvent event) {

        Notification notification = Notification.builder()
                .userId(event.getUserId())
                .message("Payment successful for Booking ID: " + event.getBookingId())
                .notificationType(NotificationType.PAYMENT)
                .status(NotificationStatus.UNREAD)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        log.info("Notification saved successfully for paymentId: {}", event.getPaymentId());
    }
}