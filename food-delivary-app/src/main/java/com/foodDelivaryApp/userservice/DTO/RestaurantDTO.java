package com.foodDelivaryApp.userservice.DTO;

import com.foodDelivaryApp.userservice.entity.CuisineType;

import com.foodDelivaryApp.userservice.Enums.PaymentMethodAcceptedEnum;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RestaurantDTO {
    private String name;
    private String address;
    private String pinCode;
    private String phoneNumber;
    private String email;
    private String website;
    private List<CuisineType> cuisineType;
    private String description;
    private String uniqueIdentifierNumber;
    private String hoursOfOperation;
    private List<String> deliveryZones; // or pinCode
    private List<PaymentMethodAcceptedEnum> paymentMethodsAccepted;


}
