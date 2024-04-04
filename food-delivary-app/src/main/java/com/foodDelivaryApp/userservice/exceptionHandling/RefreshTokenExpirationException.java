package com.foodDelivaryApp.userservice.exceptionHandling;

public class RefreshTokenExpirationException extends RuntimeException{
    public RefreshTokenExpirationException() {
    }

    public RefreshTokenExpirationException(String message) {
        super(message);
    }

    public RefreshTokenExpirationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenExpirationException(Throwable cause) {
        super(cause);
    }
}
