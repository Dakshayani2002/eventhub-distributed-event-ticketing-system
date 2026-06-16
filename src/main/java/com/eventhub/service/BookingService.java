package com.eventhub.service;

import com.eventhub.dto.BookingRequest;
import com.eventhub.dto.BookingResponse;

public interface BookingService {

    BookingResponse bookTicket(BookingRequest request);
    BookingResponse cancelBooking(Long bookingId);

}