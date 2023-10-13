package com.foodDelivaryApp.userservice.exceptionHandling;

public class InvalidOTPException extends RuntimeException{
    public InvalidOTPException() {
    }

    public InvalidOTPException(String message) {
        super(message);
    }

    public InvalidOTPException(String message, Throwable cause) {
        super(message, cause);
    }
}
