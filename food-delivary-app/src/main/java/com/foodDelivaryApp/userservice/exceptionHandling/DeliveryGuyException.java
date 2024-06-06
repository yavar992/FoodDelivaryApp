package com.foodDelivaryApp.userservice.exceptionHandling;

public class DeliveryGuyException extends RuntimeException{
    public DeliveryGuyException() {
    }

    public DeliveryGuyException(String message) {
        super(message);
    }

    public DeliveryGuyException(String message, Throwable cause) {
        super(message, cause);
    }
}
