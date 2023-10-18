package com.foodDelivaryApp.userservice.util;

public interface OTPUtil {

    static int otp() {
        return (int) ((Math.random() * 900000) + 11111);
    }

    static Integer random4digit(){
        return (int) ((Math.random() * 9000) + 1111);
    }

    static Integer random3Digit(){
        return (int) (Math.random()*900)+111;
    }

}
