package com.foodDelivaryApp.userservice.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyOTP {


    @Min(value = 100000, message = "OTP must be a 6-digit number")
    @Max(value = 999999, message = "OTP must be a 6-digit number")
    @NotNull(message = "OTP is required")
    private Integer otp;

}
