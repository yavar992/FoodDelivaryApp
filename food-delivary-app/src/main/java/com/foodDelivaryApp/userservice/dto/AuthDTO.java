package com.foodDelivaryApp.userservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthDTO {

    private String username;
    private String password;
}
