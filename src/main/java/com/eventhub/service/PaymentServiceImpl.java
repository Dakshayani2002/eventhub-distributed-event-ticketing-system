package com.eventhub.service;

import com.eventhub.dto.PaymentRequest;
import com.eventhub.dto.PaymentResponse;
import com.eventhub.entity.*;
import com.eventhub.exception.DuplicateResourceException;
import com.eventhub.exception.ResourceNotFoundException;
import com.eventhub.repository.BookingRepository;
import com.eventhub.repository.EventRepository;
import com.eventhub.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              BookingRepository bookingRepository,EventRepository eventRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.eventRepository=eventRepository;
    }

    @Transactional
    @Override
    public PaymentResponse makePayment(PaymentRequest request) {

        Booking booking = bookingRepository.findById(
                        request.getBookingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));
        log.info(
                "Payment request received. BookingId={} Amount={}",
                booking.getId(),
                booking.getTotalAmount()
        );
        Payment existingPayment =
                paymentRepository.findByBooking(booking)
                        .orElse(null);

        Payment payment;

        // If payment already successful, block payment
        if (existingPayment != null &&
                existingPayment.getStatus() == PaymentStatus.SUCCESS) {
            log.warn(
                    "Duplicate payment attempt for BookingId={}",
                    booking.getId()
            );
            throw new DuplicateResourceException(
                    "Payment already completed for this booking");
        }

        // If previous payment failed, reuse the same record
        if (existingPayment != null &&
                existingPayment.getStatus() == PaymentStatus.FAILED) {
            log.info(
                    "Retry payment for BookingId={}",
                    booking.getId()
            );
            payment = existingPayment;
        }
        else {

            // First payment attempt
            payment = new Payment();
        }

        payment.setBooking(booking);

        payment.setAmount(
                booking.getTotalAmount());

        boolean paymentSuccess = Math.random() > 0.2; // 80% success

        PaymentStatus status = paymentSuccess
                ? PaymentStatus.SUCCESS
                : PaymentStatus.FAILED;

        payment.setStatus(status);

        payment.setTransactionId(
                "TXN-" + UUID.randomUUID());

        payment.setPaymentDate(
                LocalDateTime.now());

        paymentRepository.save(payment);

        if (status == PaymentStatus.SUCCESS) {

            booking.setPaymentStatus(
                    PaymentStatus.SUCCESS);
            log.info(
                    "Payment successful. BookingId={} TransactionId={}",
                    booking.getId(),
                    payment.getTransactionId()
            );
            bookingRepository.save(booking);
        }

        PaymentResponse response =
                new PaymentResponse();

        response.setPaymentId(
                payment.getId());

        response.setBookingId(
                booking.getId());

        response.setTransactionId(
                payment.getTransactionId());

        response.setAmount(
                payment.getAmount());

        response.setStatus(
                payment.getStatus());

        response.setPaymentDate(
                payment.getPaymentDate());

        return response;
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(payment.getId());

        response.setBookingId(
                payment.getBooking().getId());

        response.setTransactionId(
                payment.getTransactionId());

        response.setAmount(
                payment.getAmount());

        response.setStatus(
                payment.getStatus());

        response.setPaymentDate(
                payment.getPaymentDate());

        return response;
    }

    @Transactional
    @Override
    public PaymentResponse refundPayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        // Prevent refund if already refunded
        if (payment.getStatus() == PaymentStatus.REFUNDED) {
            throw new DuplicateResourceException(
                    "Payment already refunded");
        }

        // Allow refund ONLY for SUCCESS payments
        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new DuplicateResourceException(
                    "Only successful payments can be refunded");
        }

        Booking booking = payment.getBooking();

        Event event = booking.getEvent();

        // Restore seats
        event.setAvailableSeats(
                event.getAvailableSeats() + booking.getNumberOfTickets()
        );

        // Update statuses
        booking.setStatus(BookingStatus.CANCELLED);
        payment.setStatus(PaymentStatus.REFUNDED);

        // Save all entities
        eventRepository.save(event);
        bookingRepository.save(booking);
        paymentRepository.save(payment);

        // Build response
        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(payment.getId());
        response.setBookingId(booking.getId());
        response.setTransactionId(payment.getTransactionId());
        response.setAmount(payment.getAmount());
        response.setStatus(payment.getStatus());
        response.setPaymentDate(payment.getPaymentDate());

        return response;
    }

    @Override
    public List<PaymentResponse> getMyPayments() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        List<Payment> payments =
                paymentRepository
                        .findByBooking_Customer_EmailOrderByPaymentDateDesc(
                                email);

        return payments.stream()
                .map(payment -> {

                    PaymentResponse response =
                            new PaymentResponse();

                    response.setPaymentId(
                            payment.getId());

                    response.setBookingId(
                            payment.getBooking().getId());

                    response.setTransactionId(
                            payment.getTransactionId());

                    response.setAmount(
                            payment.getAmount());

                    response.setStatus(
                            payment.getStatus());

                    response.setPaymentDate(
                            payment.getPaymentDate());

                    return response;
                })
                .toList();
    }
}