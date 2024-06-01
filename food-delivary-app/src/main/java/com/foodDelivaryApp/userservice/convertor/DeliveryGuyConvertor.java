package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.DTO.DeliveryGuyDTO;
import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.util.GeneratedRandomNumber;

import java.time.LocalDateTime;

public interface DeliveryGuyConvertor {
    
    static DeliveryGuy convertDeliveryGuyDTOToDeliveryGuy(DeliveryGuyDTO deliveryGuyDTO){
        return DeliveryGuy.builder()
                .username(deliveryGuyDTO.getUsername())
                .email(deliveryGuyDTO.getEmail())
                .firstName(deliveryGuyDTO.getFirstName())
                .password(deliveryGuyDTO.getPassword())
                .lastName(deliveryGuyDTO.getLastName())
                .dateOfBirth(deliveryGuyDTO.getDateOfBirth())
                .countryCode(deliveryGuyDTO.getCountryCode())
                .phoneNumber(deliveryGuyDTO.getPhoneNumber())
                .address(deliveryGuyDTO.getAddress())
                .city(deliveryGuyDTO.getCity())
                .state(deliveryGuyDTO.getState())
                .country(deliveryGuyDTO.getCountry())
                .postalCode(deliveryGuyDTO.getPostalCode())
                .createdAt(LocalDateTime.now())
                .createdBy("SELF_REGISTRATION")
                .referralCode(GeneratedRandomNumber.generateReferralCode(6))
                .isActive(true)
                .build();

    }
}
