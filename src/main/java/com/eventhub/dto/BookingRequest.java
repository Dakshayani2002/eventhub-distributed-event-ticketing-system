package com.eventhub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {

    private Long eventId;

    private Integer numberOfTickets;
}