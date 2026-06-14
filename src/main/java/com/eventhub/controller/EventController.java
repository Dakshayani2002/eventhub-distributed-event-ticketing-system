package com.eventhub.controller;
;
import com.eventhub.dto.CreateEventRequest;
import com.eventhub.dto.EventResponse;
import com.eventhub.service.EventService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/organizer/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @RequestBody CreateEventRequest request) {

        EventResponse response =
                eventService.createEvent(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<EventResponse>> getMyEvents() {

        List<EventResponse> events =
                eventService.getMyEvents();

        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(
            @PathVariable Long eventId) {

        EventResponse response =
                eventService.getEventById(eventId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long eventId, @RequestBody CreateEventRequest request) {
        EventResponse response=eventService.updateEvent(eventId,request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventResponse> patchEvent(@PathVariable Long eventId, @RequestBody CreateEventRequest request){
        EventResponse response=eventService.patchEvent(eventId,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(
            @PathVariable Long eventId) {

        eventService.deleteEvent(eventId);

        return ResponseEntity.ok("Event Deleted Successfully");
    }
}