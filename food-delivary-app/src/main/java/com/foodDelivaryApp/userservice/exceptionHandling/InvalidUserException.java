package com.foodDelivaryApp.userservice.exceptionHandling;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException() {
    }

    public InvalidUserException(String message) {
        super(message);
    }

    public InvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
