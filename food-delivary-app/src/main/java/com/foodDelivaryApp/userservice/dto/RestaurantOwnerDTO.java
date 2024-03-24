package com.foodDelivaryApp.userservice.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RestaurantOwnerDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String countryCode;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String password;


}
