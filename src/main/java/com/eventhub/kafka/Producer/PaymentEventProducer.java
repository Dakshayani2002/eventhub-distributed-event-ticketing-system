package com.eventhub.kafka.Producer;
import com.eventhub.kafka.Dto.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentEvent(PaymentEvent paymentEvent) {

        kafkaTemplate.send("payment-topic", paymentEvent);

        log.info("💳 Payment event sent to Kafka: {}", paymentEvent);
    }
}