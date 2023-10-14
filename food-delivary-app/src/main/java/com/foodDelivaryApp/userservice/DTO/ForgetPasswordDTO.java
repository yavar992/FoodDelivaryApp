package com.foodDelivaryApp.userservice.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ForgetPasswordDTO {

    private String email;
    private String otp;

}
