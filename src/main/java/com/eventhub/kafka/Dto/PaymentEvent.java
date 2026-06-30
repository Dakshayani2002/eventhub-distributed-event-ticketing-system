package com.eventhub.kafka.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEvent {

    private Long paymentId;

    private Long bookingId;

    private Long userId;

    private BigDecimal amount;

    private String paymentStatus;
}