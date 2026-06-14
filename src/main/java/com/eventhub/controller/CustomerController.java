package com.eventhub.controller;

import com.eventhub.dto.EventResponse;
import com.eventhub.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer/events")
public class CustomerController {

    private final EventService eventService;

    public CustomerController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponse> getAllEvents() {

        return eventService.getAllEvents();
    }

    @GetMapping("/{eventId}")
    public EventResponse getEventDetails(
            @PathVariable Long eventId) {

        return eventService.getEventDetails(eventId);
    }
}