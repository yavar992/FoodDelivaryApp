package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.RestaurantDTO;
import com.foodDelivaryApp.userservice.DTO.RestaurantOwnerDTO;
import com.foodDelivaryApp.userservice.DTO.VerifyOTP;
import com.foodDelivaryApp.userservice.entity.Restaurant;

import java.util.List;

public interface RestaurantsService {

    String addRestaurant(Long ownerId, RestaurantDTO restaurantDTO);

    List<RestaurantDTO> findAllRestaurant();

    String deleteRestaurant(Long ownerId, String uniqueIdentifierNumber);

    RestaurantDTO getRestaurantByUniqueIdentifierNumber(Long ownerId ,String uniqueIdentifierNumber);

    String updateRestaurant(Long ownerId, String uniqueIdentifierNumber , RestaurantDTO restaurantDTO);
}
