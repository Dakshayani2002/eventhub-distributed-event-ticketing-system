package com.eventhub.repository;

import java.util.List;

import com.eventhub.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventhub.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);

    List<Notification> findByUserIdAndStatus(
            Long userId,
            NotificationStatus status);

    long countByUserIdAndStatus(
            Long userId,
            NotificationStatus status);
}