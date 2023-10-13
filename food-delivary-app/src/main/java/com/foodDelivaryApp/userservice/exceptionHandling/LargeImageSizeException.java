package com.foodDelivaryApp.userservice.exceptionHandling;

public class LargeImageSizeException extends  RuntimeException{
    public LargeImageSizeException() {
        super();
    }

    public LargeImageSizeException(String message) {
        super(message);
    }

    public LargeImageSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LargeImageSizeException(Throwable cause) {
        super(cause);
    }
}
