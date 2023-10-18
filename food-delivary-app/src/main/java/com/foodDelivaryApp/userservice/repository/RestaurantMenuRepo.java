package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestaurantMenuRepo extends JpaRepository<RestaurantMenu , Long> {
    RestaurantMenu findByName(String name);

    @Query(value = "SELECT * FROM `restaurantmenu` WHERE foodCategoryCode =?1" , nativeQuery = true)
    Optional<RestaurantMenu> findByFoodCategoryCode(String foodCategoryCode);
}
