package com.eventhub.kafka.Producer;
import com.eventhub.kafka.Dto.BookingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "booking-topic";

    public void sendBookingCreatedEvent(BookingEvent event) {
        log.info("Sending booking event to Kafka: {}", event);

        kafkaTemplate.send(TOPIC, event);

        log.info("Booking event sent to Kafka: {}", event);
    }
}