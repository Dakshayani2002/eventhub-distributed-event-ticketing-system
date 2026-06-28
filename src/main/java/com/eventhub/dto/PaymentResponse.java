package com.eventhub.dto;

import com.eventhub.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentResponse {

    private Long paymentId;

    private Long bookingId;

    private String transactionId;

    private BigDecimal amount;

    private PaymentStatus status;

    private LocalDateTime paymentDate;
}