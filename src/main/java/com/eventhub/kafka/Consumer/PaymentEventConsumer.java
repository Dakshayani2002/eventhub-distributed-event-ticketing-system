package com.eventhub.kafka.Consumer;

import com.eventhub.kafka.Dto.PaymentEvent;
import com.eventhub.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {
    private final NotificationService notificationService;

    @KafkaListener(
            topics = "payment-topic",
            groupId = "eventhub-group"
    )
    public void consumePaymentEvent(PaymentEvent paymentEvent) {

        log.info("💰 Payment event received: {}", paymentEvent);

        notificationService.createPaymentNotification(paymentEvent);

        log.info("✅ Payment notification processed for paymentId: {}, userId: {}",
                paymentEvent.getPaymentId(),
                paymentEvent.getUserId()
        );
    }
}