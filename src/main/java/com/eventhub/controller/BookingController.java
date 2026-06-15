package com.eventhub.controller;

import com.eventhub.dto.BookingRequest;
import com.eventhub.dto.BookingResponse;
import com.eventhub.service.BookingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponse bookTicket(@RequestBody BookingRequest request) {
        return bookingService.bookTicket(request);
    }
}