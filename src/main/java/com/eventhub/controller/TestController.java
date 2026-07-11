package com.eventhub.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final CircuitBreakerRegistry registry;

    public TestController(CircuitBreakerRegistry registry) {
        this.registry = registry;
    }

    @GetMapping("/cb")
    public String test() {

        CircuitBreaker cb =
                registry.circuitBreaker("paymentCircuitBreaker");

        return cb.getState().name()
                + " | buffered="
                + cb.getMetrics().getNumberOfBufferedCalls()
                + " | failed="
                + cb.getMetrics().getNumberOfFailedCalls();
    }
}