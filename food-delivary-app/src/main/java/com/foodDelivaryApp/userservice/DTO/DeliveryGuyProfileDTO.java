package com.foodDelivaryApp.userservice.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Data
public class DeliveryGuyProfileDTO {

    private String username;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String shiftStart;
    private String shiftEnd;
    private String preferredDeliverZones;


}
