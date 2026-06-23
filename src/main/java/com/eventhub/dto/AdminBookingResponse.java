package com.eventhub.dto;

import com.eventhub.entity.BookingStatus;
import com.eventhub.entity.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AdminBookingResponse {

    private Long bookingId;

    private String customerName;

    private String customerEmail;

    private String eventTitle;

    private Integer numberOfTickets;

    private BigDecimal totalAmount;

    private BookingStatus bookingStatus;

    private PaymentStatus paymentStatus;

    private LocalDateTime bookingDate;
}