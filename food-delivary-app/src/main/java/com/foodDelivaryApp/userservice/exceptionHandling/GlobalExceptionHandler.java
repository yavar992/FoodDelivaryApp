package com.foodDelivaryApp.userservice.exceptionHandling;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.InaccessibleObjectException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Handles @Valid validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest web) {
        String firstError = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .findFirst()
                .orElse("Invalid input");

        ErrorMessage error = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                firstError,
                web.getDescription(false)
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // ✅ Fallback for all other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception ex, WebRequest web) {
        ErrorMessage error = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong: " + ex.getMessage(),
                web.getDescription(false)
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentials(BadCredentialsException ex, WebRequest web) {
        ErrorMessage error = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                "Bad Credentials",
                web.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


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

    @ExceptionHandler(CartException.class)
    public ResponseEntity<ErrorMessage> cartException(CartException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


    @ExceptionHandler(ReviewAndRatingException.class)
    public ResponseEntity<ErrorMessage> reviewAndRatingException(ReviewAndRatingException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorMessage> invalidUserDetailsException(InvalidUserException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

//    @ExceptionHandler(DeliveryException.class)
//    public ResponseEntity<ErrorMessage> deliveryException(DeliveryException ex , WebRequest web){
//        Integer statusCode = HttpStatus.BAD_REQUEST.value();
//        String message = ex.getMessage();
//        String path = web.getDescription(false);
//        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
//    }

    @ExceptionHandler(InvalidCouponException.class)
    public ResponseEntity<ErrorMessage> invalidCouponException(InvalidCouponException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ErrorMessage> invalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(OfferException.class)
    public ResponseEntity<ErrorMessage> noOfferFound(OfferException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RefreshTokenExpirationException.class)
    public ResponseEntity<ErrorMessage> refreshTokenExpirationException(RefreshTokenExpirationException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


    @ExceptionHandler(DeliveryException.class)
    public ResponseEntity<ErrorMessage> deliveryGuyException(DeliveryException ex , WebRequest web){
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        String path = web.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode, message ,path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
