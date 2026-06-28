package com.eventhub.dto;

import com.eventhub.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AdminPaymentResponse {

    private Long paymentId;

    private String customerName;

    private String customerEmail;

    private String eventTitle;

    private BigDecimal amount;

    private PaymentStatus status;

    private String transactionId;

    private LocalDateTime paymentDate;
}