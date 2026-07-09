package com.eventhub.service;

import org.springframework.stereotype.Service;

@Service
public interface PaymentGatewayService {
    boolean processPayment();
}
