package com.eventhub.service;

import com.eventhub.dto.BookingRequest;
import com.eventhub.dto.BookingResponse;
import com.eventhub.entity.AppUser;
import com.eventhub.entity.Booking;
import com.eventhub.entity.BookingStatus;
import com.eventhub.entity.Event;
import com.eventhub.exception.ResourceNotFoundException;
import com.eventhub.repository.AppUserRepository;
import com.eventhub.repository.BookingRepository;
import com.eventhub.repository.EventRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final AppUserRepository appuserRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              EventRepository eventRepository,
                              AppUserRepository appuserRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.appuserRepository = appuserRepository;
    }

    @Override
    public BookingResponse bookTicket(BookingRequest request) {

        // 1. Get logged-in user email from JWT
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        AppUser user = appuserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get event
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // 3. Check seats
        if (event.getAvailableSeats() < request.getNumberOfTickets()) {
            throw new RuntimeException("Not enough seats available");
        }

        // 4. Calculate total price
        BigDecimal totalAmount =
                BigDecimal.valueOf(event.getPrice())
                        .multiply(BigDecimal.valueOf(request.getNumberOfTickets()));

        // 5. Create booking
        Booking booking = new Booking();
        booking.setCustomer(user);
        booking.setEvent(event);
        booking.setNumberOfTickets(request.getNumberOfTickets());
        booking.setTotalAmount(totalAmount);
        booking.setBookingDate(LocalDateTime.now());

        // 6. Reduce seats
        event.setAvailableSeats(
                event.getAvailableSeats() - request.getNumberOfTickets()
        );
        booking.setStatus(BookingStatus.BOOKED);

        // 7. Save
        bookingRepository.save(booking);
        eventRepository.save(event);

        // 8. Response
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setEventId(event.getId());
        response.setEventTitle(event.getTitle());
        response.setNumberOfTickets(booking.getNumberOfTickets());
        response.setTotalAmount(booking.getTotalAmount());
        response.setBookingDate(booking.getBookingDate());

        return response;
    }

    @Override
    public BookingResponse cancelBooking(Long bookingId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        if (!booking.getCustomer().getEmail().equals(email)) {
            throw new RuntimeException("You cannot cancel another customer's booking");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking already cancelled");
        }

        Event event = booking.getEvent();

        event.setAvailableSeats(
                event.getAvailableSeats() + booking.getNumberOfTickets()
        );

        booking.setStatus(BookingStatus.CANCELLED);

        eventRepository.save(event);
        bookingRepository.save(booking);

        BookingResponse response = new BookingResponse();

        response.setBookingId(booking.getId());
        response.setEventTitle(event.getTitle());
        response.setNumberOfTickets(booking.getNumberOfTickets());
        response.setTotalAmount(booking.getTotalAmount());
        response.setEventId(event.getId());
        response.setBookingDate(booking.getBookingDate());

        return response;
    }
}