package com.eventhub.controller;

import com.eventhub.dto.AdminBookingResponse;
import com.eventhub.dto.AdminPaymentResponse;
import com.eventhub.dto.EventResponse;
import com.eventhub.dto.UserResponse;
import com.eventhub.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(
            AdminService adminService) {

        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>>
    getAllUsers() {

        return ResponseEntity.ok(
                adminService.getAllUsers());
    }

    @GetMapping("/events")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EventResponse>> getAllEventsForAdmin() {
        return ResponseEntity.ok(adminService.getAllEvents());
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<AdminBookingResponse>>
    getAllBookings() {

        return ResponseEntity.ok(
                adminService.getAllBookings());
    }

    @GetMapping("/payments")
    public ResponseEntity<List<AdminPaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(adminService.getAllPayments());
    }


}