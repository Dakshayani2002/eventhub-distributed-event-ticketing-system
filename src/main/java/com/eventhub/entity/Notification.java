package com.eventhub.entity;

import java.time.LocalDateTime;
import com.eventhub.enums.NotificationStatus;
import com.eventhub.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private LocalDateTime createdAt;

    public Notification() {
    }

    public Notification(Long userId, String message,
                        NotificationType notificationType,
                        NotificationStatus status,
                        LocalDateTime createdAt) {
        this.userId = userId;
        this.message = message;
        this.notificationType = notificationType;
        this.status = status;
        this.createdAt = createdAt;
    }
}