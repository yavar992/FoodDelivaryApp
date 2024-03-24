package com.foodDelivaryApp.userservice.service;

import java.util.List;

import com.foodDelivaryApp.userservice.dto.RestaurantDTO;
import com.foodDelivaryApp.userservice.entity.CuisineType;
import com.foodDelivaryApp.userservice.entity.Restaurant;

public interface IRestaurantsService {

    Restaurant findByEmail(String email);

    String addRestaurant(Long ownerId, RestaurantDTO restaurantDTO);

    List<RestaurantDTO> findAllRestaurant();

    String deleteRestaurant(Long ownerId, String uniqueIdentifierNumber);

    RestaurantDTO getRestaurantByUniqueIdentifierNumber(Long ownerId ,String uniqueIdentifierNumber);

    String updateRestaurant(Long ownerId, String uniqueIdentifierNumber , RestaurantDTO restaurantDTO);

    List<CuisineType> findAllCuisineTypes(Long ownerId, String uniqueIdentifierNumber);
}
