package com.foodDelivaryApp.userservice.util;

public interface OTPUtil {

    public static int generateOTP() {
        return (int) ((Math.random() * 900000) + 11111);
    }

    public static Integer random4digit(){
        return (int) ((Math.random() * 9000) + 1111);
    }

    public static Integer random3Digit(){
        return (int) (Math.random()*900)+111;
    }

}
