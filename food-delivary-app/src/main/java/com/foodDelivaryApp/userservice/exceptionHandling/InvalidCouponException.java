package com.foodDelivaryApp.userservice.exceptionHandling;

public class InvalidCouponException extends RuntimeException {
    public InvalidCouponException() {
    }

    public InvalidCouponException(String message) {
        super(message);
    }

    public InvalidCouponException(String message, Throwable cause) {
        super(message, cause);
    }
}
