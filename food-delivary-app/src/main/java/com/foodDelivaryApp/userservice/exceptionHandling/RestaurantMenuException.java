package com.foodDelivaryApp.userservice.exceptionHandling;

public class RestaurantMenuException extends RuntimeException{
    public RestaurantMenuException() {
    }

    public RestaurantMenuException(String message) {
        super(message);
    }

    public RestaurantMenuException(String message, Throwable cause) {
        super(message, cause);
    }
}
