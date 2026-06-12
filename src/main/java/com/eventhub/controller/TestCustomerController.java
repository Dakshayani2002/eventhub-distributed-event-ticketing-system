package com.eventhub.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearerAuth")

@RestController
@RequestMapping("/api/customer")
public class TestCustomerController {

    @GetMapping("/dashboard")
    public String customerDashboard() {
        return "Welcome Customer Dashboard!";
    }
}