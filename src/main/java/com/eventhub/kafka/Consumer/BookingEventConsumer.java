package com.eventhub.kafka.consumer;

import com.eventhub.kafka.Dto.BookingEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.eventhub.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "booking-topic", groupId = "eventhub-group")
    public void consume(BookingEvent event) {

        log.info("📩 Booking event received: {}", event);

        notificationService.createBookingNotification(event);
    }
}