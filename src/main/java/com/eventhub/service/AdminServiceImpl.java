package com.eventhub.service;

import com.eventhub.dto.AdminBookingResponse;
import com.eventhub.dto.AdminPaymentResponse;
import com.eventhub.dto.EventResponse;
import com.eventhub.dto.UserResponse;
import com.eventhub.entity.AppUser;
import com.eventhub.entity.Event;
import com.eventhub.repository.AppUserRepository;
import com.eventhub.repository.BookingRepository;
import com.eventhub.repository.EventRepository;
import com.eventhub.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import com.eventhub.entity.Booking;
import com.eventhub.entity.Payment;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AppUserRepository appUserRepository;
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public AdminServiceImpl(
            AppUserRepository appUserRepository,EventRepository eventRepository,BookingRepository bookingRepository,PaymentRepository paymentRepository) {

        this.appUserRepository = appUserRepository;
        this.eventRepository=eventRepository;
        this.bookingRepository=bookingRepository;
        this.paymentRepository=paymentRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() {

        List<AppUser> users =
                appUserRepository.findAll();

        return users.stream()
                .map(user -> {

                    UserResponse response =
                            new UserResponse();

                    response.setId(user.getId());

                    response.setName(user.getName());

                    response.setEmail(user.getEmail());

                    response.setRole(user.getRole());

                    return response;
                })
                .toList();
    }

    @Override
    public List<EventResponse> getAllEvents() {

        List<Event> events = eventRepository.findAll();

        return events.stream().map(event -> {

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

            // Organizer info (important in admin view)
            response.setOrganizerId(event.getCreatedBy().getId());
            response.setOrganizerName(event.getCreatedBy().getName()); // if name exists

            return response;

        }).toList();
    }

    @Override
    public List<AdminBookingResponse> getAllBookings() {

        List<Booking> bookings =
                bookingRepository.findAll();

        return bookings.stream()
                .map(booking -> {

                    AdminBookingResponse response =
                            new AdminBookingResponse();

                    response.setBookingId(
                            booking.getId());

                    response.setCustomerName(
                            booking.getCustomer().getName());

                    response.setCustomerEmail(
                            booking.getCustomer().getEmail());

                    response.setEventTitle(
                            booking.getEvent().getTitle());

                    response.setNumberOfTickets(
                            booking.getNumberOfTickets());

                    response.setTotalAmount(
                            booking.getTotalAmount());

                    response.setBookingStatus(
                            booking.getStatus());

                    response.setPaymentStatus(
                            booking.getPaymentStatus());

                    response.setBookingDate(
                            booking.getBookingDate());

                    return response;
                })
                .toList();
    }

    @Override
    public List<AdminPaymentResponse> getAllPayments() {

        List<Payment> payments =
                paymentRepository.findAll();

        return payments.stream()
                .map(payment -> {

                    AdminPaymentResponse response =
                            new AdminPaymentResponse();

                    response.setPaymentId(
                            payment.getId());

                    response.setCustomerName(
                            payment.getBooking()
                                    .getCustomer()
                                    .getName());

                    response.setCustomerEmail(
                            payment.getBooking()
                                    .getCustomer()
                                    .getEmail());

                    response.setEventTitle(
                            payment.getBooking()
                                    .getEvent()
                                    .getTitle());

                    response.setAmount(
                            payment.getAmount());

                    response.setStatus(
                            payment.getStatus());

                    response.setTransactionId(
                            payment.getTransactionId());

                    response.setPaymentDate(
                            payment.getPaymentDate());

                    return response;

                })
                .toList();
    }
}