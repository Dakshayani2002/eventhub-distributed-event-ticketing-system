package com.eventhub.controller;

import com.eventhub.dto.*;
import com.eventhub.entity.AppUser;
import com.eventhub.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse savedUser = appUserService.registerUser(request);

        ApiResponse<UserResponse> response =
                new ApiResponse<>(true, "User registered successfully", savedUser);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(
            @Valid@RequestBody LoginRequest request
            ){
        LoginResponse loginResponse=appUserService.loginUser(request);
        ApiResponse<LoginResponse>response =new ApiResponse<>(true,"Login Successful",loginResponse);
        return ResponseEntity.ok(response);
    }
}