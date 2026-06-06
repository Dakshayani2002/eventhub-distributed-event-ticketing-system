package com.eventhub.service;

import com.eventhub.dto.RegisterRequest;
import com.eventhub.dto.UserResponse;
import com.eventhub.entity.AppUser;
import com.eventhub.exception.DuplicateResourceException;
import com.eventhub.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    
    public AppUserService(AppUserRepository appUserRepository,PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setRole(request.getRole());
        AppUser savedUser = appUserRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());

        return response;

    }
}