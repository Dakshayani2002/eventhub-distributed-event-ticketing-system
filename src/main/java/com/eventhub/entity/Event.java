package com.eventhub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String venue;

    private LocalDate eventDate;

    private LocalTime eventTime;

    private Integer totalSeats;

    private Integer availableSeats;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private AppUser createdBy;

    public Event() {
    }

}
