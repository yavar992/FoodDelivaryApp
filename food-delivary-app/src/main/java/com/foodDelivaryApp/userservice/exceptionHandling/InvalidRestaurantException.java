package com.foodDelivaryApp.userservice.exceptionHandling;

public class InvalidRestaurantException extends RuntimeException{
    public InvalidRestaurantException() {
    }

    public InvalidRestaurantException(String message) {
        super(message);
    }

    public InvalidRestaurantException(String message, Throwable cause) {
        super(message, cause);
    }
}
