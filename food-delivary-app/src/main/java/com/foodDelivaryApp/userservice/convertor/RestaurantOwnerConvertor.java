package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.dto.RestaurantOwnerDTO;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;

import java.time.LocalDateTime;

public interface RestaurantOwnerConvertor {

    static RestaurantOwner convertRestaurantOwnerDTOToRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO){
        return RestaurantOwner.builder()
                .firstName(restaurantOwnerDTO.getFirstName())
                .lastName(restaurantOwnerDTO.getLastName())
                .email(restaurantOwnerDTO.getEmail())
                .state(restaurantOwnerDTO.getState())
                .phoneNumber(restaurantOwnerDTO.getPhoneNumber())
                .countryCode(restaurantOwnerDTO.getCountryCode())
                .password(restaurantOwnerDTO.getPassword())
                .city(restaurantOwnerDTO.getCity())
                .state(restaurantOwnerDTO.getState())
                .address(restaurantOwnerDTO.getAddress())
                .zipCode(restaurantOwnerDTO.getZipCode())
                .createdAt(LocalDateTime.now())
                .build();
    }
}

