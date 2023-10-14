package com.foodDelivaryApp.userservice.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@RequiredArgsConstructor
public class ChangePasswordDTO {

    private Integer otp;
    private String password;
}
