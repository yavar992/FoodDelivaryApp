package com.foodDelivaryApp.userservice.exceptionHandling;

public class ReviewAndRatingException extends RuntimeException{
    public ReviewAndRatingException() {
    }

    public ReviewAndRatingException(String message) {
        super(message);
    }

    public ReviewAndRatingException(String message, Throwable cause) {
        super(message, cause);
    }
}
