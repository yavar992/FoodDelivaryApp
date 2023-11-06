package com.foodDelivaryApp.userservice.exceptionHandling;

public class DeliveryException extends RuntimeException{
    public DeliveryException() {
    }

    public DeliveryException(String message) {
        super(message);
    }

    public DeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
