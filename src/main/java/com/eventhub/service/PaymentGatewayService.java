package com.eventhub.service;

import org.springframework.stereotype.Service;

public interface PaymentGatewayService {
    boolean processPayment();
}
