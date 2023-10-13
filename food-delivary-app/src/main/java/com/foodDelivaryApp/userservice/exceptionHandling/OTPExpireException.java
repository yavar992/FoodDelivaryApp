package com.foodDelivaryApp.userservice.exceptionHandling;

public class OTPExpireException extends RuntimeException{
    public OTPExpireException() {
    }

    public OTPExpireException(String message) {
        super(message);
    }

    public OTPExpireException(String message, Throwable cause) {
        super(message, cause);
    }
}
