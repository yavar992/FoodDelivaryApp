package com.foodDelivaryApp.userservice.exceptionHandling;

public class UnverifiedUserException extends RuntimeException{
    public UnverifiedUserException() {
    }

    public UnverifiedUserException(String message) {
        super(message);
    }

    public UnverifiedUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
