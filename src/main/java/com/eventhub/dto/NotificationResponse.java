package com.eventhub.dto;

import java.time.LocalDateTime;

import com.eventhub.enums.NotificationStatus;
import com.eventhub.enums.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long id;

    private String message;

    private NotificationType notificationType;

    private NotificationStatus status;

    private LocalDateTime createdAt;

}