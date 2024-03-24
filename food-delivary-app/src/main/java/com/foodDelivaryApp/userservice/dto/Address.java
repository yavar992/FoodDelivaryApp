package com.foodDelivaryApp.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

    private String phoneNumber;
    private String addressTypes; //i.e home , office , ...
    private String line1;
    private String line2;
    private String city;
    private String countryCode;
    private String postalCode;
    private String state;
}
