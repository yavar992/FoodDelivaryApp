package com.foodDelivaryApp.userservice.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorMessage {

    private Integer statusCode;
    private String message;
    private String path;
}
