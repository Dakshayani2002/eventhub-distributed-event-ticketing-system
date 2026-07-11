package com.eventhub.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    @Override
    @Retry(name = "paymentRetry", fallbackMethod = "retryFallback")
//    @CircuitBreaker(
//            name = "paymentCircuitBreaker",
//            fallbackMethod = "circuitBreakerFallback"
//    )

    public boolean processPayment() {

        log.info("Calling External Payment Gateway...");

        // Simulate temporary gateway failure (70% chance)
        if (Math.random() < 0.7) {
            log.warn("Payment Gateway Temporary Failure");
            throw new RuntimeException("Payment Gateway Down");
        }

        log.info("Payment Gateway Success");

        return true;
    }

    public boolean retryFallback(RuntimeException ex) {
        log.error("========== RETRY FALLBACK ==========");
        log.error("Retry exhausted: {}", ex.getMessage());
        return false;
    }

//    public boolean circuitBreakerFallback(RuntimeException ex) {
//        log.error("========== CIRCUIT BREAKER FALLBACK ==========");
//        log.error("Circuit Breaker fallback: {}", ex.getMessage());
//        return false;
//    }
}