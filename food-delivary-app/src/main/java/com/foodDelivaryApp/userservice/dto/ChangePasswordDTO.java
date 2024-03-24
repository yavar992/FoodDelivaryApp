package com.foodDelivaryApp.userservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChangePasswordDTO {

    private Integer otp;
    private String password;
}
