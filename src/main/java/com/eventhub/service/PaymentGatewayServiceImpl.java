package com.eventhub.service;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    @Override
    @Retry(name = "paymentRetry", fallbackMethod = "paymentFallback")
    public boolean processPayment() {

        log.info("Calling External Payment Gateway...");

        // Simulate temporary gateway failure (70% chance)
        if (Math.random() < 0.7) {
            log.warn("Payment Gateway Temporary Failure");
            throw new RuntimeException("Temporary Gateway Failure");
        }

        log.info("Payment Gateway Success");

        return true;
    }

    public boolean paymentFallback(RuntimeException ex) {

        log.error("Fallback executed: {}", ex.getMessage());

        return false;
    }
}