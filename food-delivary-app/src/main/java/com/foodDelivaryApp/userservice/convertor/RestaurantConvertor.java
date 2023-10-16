package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.DTO.RestaurantDTO;
import com.foodDelivaryApp.userservice.entity.CuisineType;
import com.foodDelivaryApp.userservice.entity.PaymentMethodAccepted;
import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.util.OTPUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface RestaurantConvertor {

    static Restaurant convertRestaurantDTOToRestaurant(RestaurantDTO restaurantDTO){
        return Restaurant.builder()
                .name(restaurantDTO.getName())
                .website(restaurantDTO.getWebsite())
                .email(restaurantDTO.getEmail())
                .address(restaurantDTO.getAddress())
                .pinCode(restaurantDTO.getPinCode())
                .deliveryZones(restaurantDTO.getDeliveryZones())
                .hoursOfOperation(restaurantDTO.getHoursOfOperation())
                .phoneNumber(restaurantDTO.getPhoneNumber())
                .description(restaurantDTO.getDescription())
                .createdAt(LocalDateTime.now())
                .uniqueIdentifierNumber("HappyMeal"+ LocalDateTime.now().getYear() +OTPUtil.random4digit())
                .cuisineType(restaurantDTO.getCuisineType())
                .paymentMethodsAccepted(restaurantDTO.getPaymentMethodsAccepted())
                .build();
    }

    static RestaurantDTO convertRestaurantToRestaurantDTO(Restaurant restaurant){
        return RestaurantDTO.builder()
                .name(restaurant.getName())
                .email(restaurant.getEmail())
                .phoneNumber(restaurant.getPhoneNumber())
                .pinCode(restaurant.getPinCode())
                .address(restaurant.getAddress())
                .hoursOfOperation(restaurant.getHoursOfOperation())
                .paymentMethodsAccepted(restaurant.getPaymentMethodsAccepted())
                .deliveryZones(restaurant.getDeliveryZones())
                .website(restaurant.getWebsite())
                .cuisineType(restaurant.getCuisineType())
                .description(restaurant.getDescription())
                .uniqueIdentifierNumber(restaurant.getUniqueIdentifierNumber())
                .build();
    }

    static void updateRestaurant(RestaurantDTO restaurantDTO , Restaurant restaurant){
            restaurant.setName(restaurantDTO.getName());
            restaurant.setUpdatedAt(LocalDateTime.now());
            restaurant.setPinCode(restaurantDTO.getPinCode());
            restaurant.setCuisineType(restaurantDTO.getCuisineType());
            restaurant.setEmail(restaurantDTO.getEmail());
            restaurant.setAddress(restaurantDTO.getAddress());
            restaurant.setWebsite(restaurantDTO.getWebsite());
            restaurant.setHoursOfOperation(restaurantDTO.getHoursOfOperation());
            restaurant.setDeliveryZones(restaurantDTO.getDeliveryZones());
            restaurant.setPhoneNumber(restaurantDTO.getPhoneNumber());
            restaurant.setDescription(restaurantDTO.getDescription());
            restaurant.setPaymentMethodsAccepted(restaurantDTO.getPaymentMethodsAccepted());



    }
}
