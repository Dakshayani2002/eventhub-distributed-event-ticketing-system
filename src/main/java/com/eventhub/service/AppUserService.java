package com.eventhub.service;

import com.eventhub.dto.LoginRequest;
import com.eventhub.dto.LoginResponse;
import com.eventhub.dto.RegisterRequest;
import com.eventhub.dto.UserResponse;
import com.eventhub.entity.AppUser;
import com.eventhub.enums.Role;
import com.eventhub.exception.DuplicateResourceException;
import com.eventhub.repository.AppUserRepository;
import com.eventhub.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    public AppUserService(AppUserRepository appUserRepository,PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
    }
    public UserResponse registerUser(RegisterRequest request) {
        if (appUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }

        AppUser user = new AppUser();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        //before encrypting
        //user.setPassword(request.getPassword());

        //after encrypting using bcrypt
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        AppUser savedUser = appUserRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());

        return response;

    }
    public LoginResponse loginUser(LoginRequest request){
        AppUser appUser=appUserRepository.findByEmail(request.getEmail()).orElseThrow(()->new DuplicateResourceException("User not found"));
        if(!passwordEncoder.matches(request.getPassword(), appUser.getPassword())){
            throw new RuntimeException("Invalid Password");
        }
        String token= jwtService.generateToken(appUser.getEmail());
        return new LoginResponse(token, appUser.getEmail(), appUser.getRole());
    }
}