package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.DTO.RestaurantMenuDTO;
import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.entity.RestaurantMenu;
import com.foodDelivaryApp.userservice.util.OTPUtil;

import java.time.LocalDateTime;

public interface RestaurantMenuConvertor {

    static RestaurantMenu convertRestaurantMenuDTToRestaurantMenu(RestaurantMenuDTO restaurantMenuDTO){
        return RestaurantMenu.builder()
                .name(restaurantMenuDTO.getName())
                .addedDate(LocalDateTime.now())
                .cuisineType(restaurantMenuDTO.getCuisineType())
                .foodCategoryCode(restaurantMenuDTO.getName().substring(0,3)+ OTPUtil.random4digit())
                .build();
    }

    static void updateRestaurantMenu(RestaurantMenuDTO restaurantMenuDTO , RestaurantMenu restaurantMenu){
        restaurantMenu.setName(restaurantMenuDTO.getName());
        restaurantMenu.setCuisineType(restaurantMenuDTO.getCuisineType());
        restaurantMenu.setFoodCategoryCode(restaurantMenuDTO.getName().substring(0,3)+OTPUtil.random4digit());
        restaurantMenu.setUpdateDate(LocalDateTime.now());
    }
}
