package com.eventhub.kafka.Consumer;

import com.eventhub.kafka.Dto.BookingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingEventConsumer {

    @KafkaListener(topics = "booking-topic")
    public void consumeBookingEvent(BookingEvent event) {

        log.info("📩 Booking event received: {}", event);

        log.info("✅ Notification sent for bookingId: {}", event.getBookingId());
    }
}