package com.foodDelivaryApp.userservice.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(LargeImageSizeException.class)
    public ResponseEntity<ErrorMessage> largeImageSizeException(LargeImageSizeException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(OTPExpireException.class)
    public ResponseEntity<ErrorMessage> otpExpireException(OTPExpireException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidOTPException.class)
    public ResponseEntity<ErrorMessage> invalidOTPException(InvalidOTPException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(UnverifiedUserException.class)
    public ResponseEntity<ErrorMessage> unverifiedUserException(UnverifiedUserException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
