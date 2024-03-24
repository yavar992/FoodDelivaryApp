package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.dto.RestaurantMenuDTO;
import com.foodDelivaryApp.userservice.entity.RestaurantMenu;

import java.util.List;

public interface IRestaurantMenuServices {
    RestaurantMenu addMenuToRestaurant(Long ownerId, RestaurantMenuDTO restaurantMenu, String uniqueIdentifierNumber);

    boolean foodAlreadyExistByCategory(String name);

    List<RestaurantMenu> findAAllRestaurntsMenu();

    RestaurantMenu findRestaurantByName(Long ownerId, String uniqueIdentifierNumber, String foodCategoryCode);

    List<RestaurantMenu> findAllRestaurantMenuInPaginationMode(Long ownerId, String uniqueIdentifierNumber, Integer pageNo, Integer pageSize, String sortBy , String sortOrder);

    RestaurantMenu updateMenu(Long ownerId, String uniqueIdentifierNumber, RestaurantMenuDTO restaurantMenuDTO, String foodCategoryCode);

    String deleteMenu(Long ownerId, String uniqueIdentifierNumber, String foodCategoryCode);
}
