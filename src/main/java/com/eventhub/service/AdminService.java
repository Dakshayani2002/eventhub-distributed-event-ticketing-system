package com.eventhub.service;

import com.eventhub.dto.AdminBookingResponse;
import com.eventhub.dto.AdminPaymentResponse;
import com.eventhub.dto.EventResponse;
import com.eventhub.dto.UserResponse;

import java.util.List;

public interface AdminService {

    List<UserResponse> getAllUsers();
    List<EventResponse> getAllEvents();
    List<AdminBookingResponse> getAllBookings();
    List<AdminPaymentResponse> getAllPayments();
}