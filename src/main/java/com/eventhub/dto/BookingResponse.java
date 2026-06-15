package com.eventhub.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingResponse {

    private Long bookingId;

    private Long eventId;

    private String eventTitle;

    private Integer numberOfTickets;

    private BigDecimal totalAmount;

    private LocalDateTime bookingDate;
}