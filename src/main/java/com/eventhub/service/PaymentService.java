package com.eventhub.service;

import com.eventhub.dto.PaymentRequest;
import com.eventhub.dto.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest request);
    PaymentResponse getPaymentById(Long paymentId);
    PaymentResponse refundPayment(Long paymentId);
    List<PaymentResponse> getMyPayments();
}