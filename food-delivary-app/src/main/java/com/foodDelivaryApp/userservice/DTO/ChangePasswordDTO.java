package com.foodDelivaryApp.userservice.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChangePasswordDTO {

    private Integer otp;
    private String password;
}
