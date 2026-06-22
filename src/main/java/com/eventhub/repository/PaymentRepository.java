package com.eventhub.repository;

import com.eventhub.entity.Booking;
import com.eventhub.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBooking(Booking booking);
    List<Payment> findByBooking_Customer_EmailOrderByPaymentDateDesc(
            String email);
}