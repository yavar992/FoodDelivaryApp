package com.foodDelivaryApp.userservice.exceptionHandling;

public class OfferException extends RuntimeException{
    public OfferException() {
    }

    public OfferException(String message) {
        super(message);
    }

    public OfferException(String message, Throwable cause) {
        super(message, cause);
    }
}
