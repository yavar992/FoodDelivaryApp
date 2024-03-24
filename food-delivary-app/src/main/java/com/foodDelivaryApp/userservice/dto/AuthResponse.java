package com.foodDelivaryApp.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    
    private String loginUser;
    private String token;
    
}
