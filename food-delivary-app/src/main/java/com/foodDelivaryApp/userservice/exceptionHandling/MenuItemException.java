package com.foodDelivaryApp.userservice.exceptionHandling;

public class MenuItemException extends RuntimeException{
    public MenuItemException() {
    }

    public MenuItemException(String message) {
        super(message);
    }

    public MenuItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
