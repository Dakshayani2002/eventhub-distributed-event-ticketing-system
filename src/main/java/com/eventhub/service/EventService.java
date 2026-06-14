package com.eventhub.service;
import com.eventhub.dto.CreateEventRequest;
import com.eventhub.dto.EventResponse;

import java.util.List;

public interface EventService {

    EventResponse createEvent(CreateEventRequest request);
    List<EventResponse> getMyEvents();
    EventResponse getEventById(Long eventId);
    EventResponse updateEvent(
            Long eventId,
            CreateEventRequest request);
    EventResponse patchEvent(
            Long eventId,
            CreateEventRequest request);
    void deleteEvent(Long eventId);
    List<EventResponse> getAllEvents();
    EventResponse getEventDetails(Long eventId);
}