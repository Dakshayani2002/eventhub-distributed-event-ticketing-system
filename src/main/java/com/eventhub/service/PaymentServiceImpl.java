package com.eventhub.service;

import com.eventhub.dto.PaymentRequest;
import com.eventhub.dto.PaymentResponse;
import com.eventhub.entity.Booking;
import com.eventhub.entity.Payment;
import com.eventhub.entity.PaymentStatus;
import com.eventhub.exception.DuplicateResourceException;
import com.eventhub.exception.ResourceNotFoundException;
import com.eventhub.repository.BookingRepository;
import com.eventhub.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    @Override
    public PaymentResponse makePayment(PaymentRequest request) {

        Booking booking = bookingRepository.findById(
                        request.getBookingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        Payment existingPayment =
                paymentRepository.findByBooking(booking)
                        .orElse(null);

        Payment payment;

        // If payment already successful, block payment
        if (existingPayment != null &&
                existingPayment.getStatus() == PaymentStatus.SUCCESS) {

            throw new DuplicateResourceException(
                    "Payment already completed for this booking");
        }

        // If previous payment failed, reuse the same record
        if (existingPayment != null &&
                existingPayment.getStatus() == PaymentStatus.FAILED) {

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
}