package com.foodDelivaryApp.userservice.util;

import org.springframework.stereotype.Service;

public interface OTPUtil {

    static int otp() {
        return (int) ((Math.random() * 900000) + 11111);
    }



}
