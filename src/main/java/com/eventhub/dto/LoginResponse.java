package com.eventhub.dto;

import com.eventhub.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String token;
    private String email;
    private Role role;

    public LoginResponse() {
    }

    public LoginResponse(String token, String email, Role role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

}