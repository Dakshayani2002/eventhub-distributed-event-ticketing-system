package com.eventhub.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private String venue;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double price;

    public EventResponse() {
    }

}