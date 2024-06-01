package com.foodDelivaryApp.userservice.DTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class DeliveryGuyDTO {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String countryCode;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String preferredDeliverZones;
    private LocalDateTime createdAt;
    private String createdBy;
    private String referralCode;
    private boolean isActive;



}
