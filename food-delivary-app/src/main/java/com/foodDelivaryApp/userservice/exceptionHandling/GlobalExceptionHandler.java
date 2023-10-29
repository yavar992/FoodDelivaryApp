package com.foodDelivaryApp.userservice.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.InaccessibleObjectException;

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

    @ExceptionHandler(InvalidRestaurantException.class)
    public ResponseEntity<ErrorMessage> invalidRestaurantException(InvalidRestaurantException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> messageNotReadableException(HttpMessageNotReadableException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RestaurantMenuException.class)
    public ResponseEntity<ErrorMessage> restaurantMenuException(RestaurantMenuException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorMessage> missingPathVariableException(MissingPathVariableException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


    @ExceptionHandler(MenuItemException.class)
    public ResponseEntity<ErrorMessage> menuItemException(MenuItemException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InaccessibleObjectException.class)
    public ResponseEntity<ErrorMessage> inaccessibleObjectException(InaccessibleObjectException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessage> nullPointerException(NullPointerException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
