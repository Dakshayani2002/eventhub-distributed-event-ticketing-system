package com.eventhub.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class CreateEventRequest {

    private String title;
    private String description;
    private String venue;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private Integer totalSeats;
    private Double price;

    public CreateEventRequest() {
    }

}