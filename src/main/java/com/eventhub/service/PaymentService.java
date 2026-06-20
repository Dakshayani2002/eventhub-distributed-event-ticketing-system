package com.eventhub.service;

import com.eventhub.dto.PaymentRequest;
import com.eventhub.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest request);
    PaymentResponse getPaymentById(Long paymentId);
}