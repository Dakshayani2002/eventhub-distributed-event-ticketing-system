package com.eventhub.kafka.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent {

    private Long bookingId;
    private Long userId;
    private Long eventId;
    private String status;
}