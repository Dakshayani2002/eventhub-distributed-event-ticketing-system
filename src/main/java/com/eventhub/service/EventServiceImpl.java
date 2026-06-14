package com.eventhub.service;

import com.eventhub.dto.CreateEventRequest;
import com.eventhub.dto.EventResponse;
import com.eventhub.entity.AppUser;
import com.eventhub.entity.Event;
import com.eventhub.exception.ResourceNotFoundException;
import com.eventhub.repository.AppUserRepository;
import com.eventhub.repository.EventRepository;
import com.eventhub.service.EventService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final AppUserRepository appUserRepository;

    public EventServiceImpl(EventRepository eventRepository, AppUserRepository appUserRepository) {
        this.eventRepository = eventRepository;
        this.appUserRepository=appUserRepository;
    }

    @Override
    public EventResponse createEvent(CreateEventRequest request) {

        Event event = new Event();

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setVenue(request.getVenue());
        event.setEventDate(request.getEventDate());
        event.setEventTime(request.getEventTime());
        event.setTotalSeats(request.getTotalSeats());

        event.setAvailableSeats(request.getTotalSeats());

        event.setPrice(request.getPrice());
        // 🔥 GET LOGGED IN USER FROM JWT
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

// 🔥 FETCH USER FROM DB
        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

// 🔥 SET ORGANIZER (THIS FIXES YOUR NULL ISSUE)
        event.setCreatedBy(user);

        Event savedEvent = eventRepository.save(event);

        return mapToResponse(savedEvent);
    }

    @Override
    public List<EventResponse> getMyEvents() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        AppUser organizer = appUserRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        List<Event> events =
                eventRepository.findByCreatedBy(organizer);

        return events.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EventResponse getEventById(Long eventId) {

        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found"));
        AppUser loggedInUser = getLoggedInUser();

        if (!event.getCreatedBy().getId()
                .equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "Access Denied");
        }
        return mapToResponse(event);
    }

    @Override
    public EventResponse updateEvent(
            Long eventId,
            CreateEventRequest request) {

        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found"));
        AppUser loggedInUser = getLoggedInUser();

        if (!event.getCreatedBy().getId()
                .equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "Access Denied");
        }

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setVenue(request.getVenue());
        event.setEventDate(request.getEventDate());
        event.setEventTime(request.getEventTime());
        event.setTotalSeats(request.getTotalSeats());
        event.setPrice(request.getPrice());

        Event updatedEvent =
                eventRepository.save(event);

        return mapToResponse(updatedEvent);
    }

    @Override
    public EventResponse patchEvent(
            Long eventId,
            CreateEventRequest request) {

        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found"));
        AppUser loggedInUser = getLoggedInUser();

        if (!event.getCreatedBy().getId()
                .equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "Access Denied");
        }

        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }

        if (request.getVenue() != null) {
            event.setVenue(request.getVenue());
        }

        if (request.getEventDate() != null) {
            event.setEventDate(request.getEventDate());
        }

        if (request.getEventTime() != null) {
            event.setEventTime(request.getEventTime());
        }

        if (request.getTotalSeats() != null) {
            event.setTotalSeats(request.getTotalSeats());
        }

        if (request.getPrice() != null) {
            event.setPrice(request.getPrice());
        }

        Event updatedEvent = eventRepository.save(event);

        return mapToResponse(updatedEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event=eventRepository.findById(eventId).orElseThrow(()-> new ResourceNotFoundException("Event not found"));
        AppUser loggedInUser = getLoggedInUser();

        if (!event.getCreatedBy().getId()
                .equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "Access Denied");
        }
        eventRepository.delete(event);
    }

    @Override
    public List<EventResponse> getAllEvents() {

        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EventResponse getEventDetails(Long eventId) {

        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found"));

        return mapToResponse(event);
    }

    private EventResponse mapToResponse(Event event) {

        EventResponse response = new EventResponse();

        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setVenue(event.getVenue());
        response.setEventDate(event.getEventDate());
        response.setEventTime(event.getEventTime());
        response.setTotalSeats(event.getTotalSeats());
        response.setAvailableSeats(event.getAvailableSeats());
        response.setPrice(event.getPrice());

        return response;
    }

    private AppUser getLoggedInUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return appUserRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));
    }

}
