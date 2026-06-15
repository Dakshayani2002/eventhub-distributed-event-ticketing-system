package com.eventhub.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private AppUser customer;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private LocalDateTime bookingDate;

    private Integer numberOfTickets;

    private BigDecimal totalAmount;
}