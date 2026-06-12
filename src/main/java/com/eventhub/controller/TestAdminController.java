package com.eventhub.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearerAuth")

@RestController
@RequestMapping("/api/admin")
public class TestAdminController {

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Welcome Admin Dashboard!";
    }
}