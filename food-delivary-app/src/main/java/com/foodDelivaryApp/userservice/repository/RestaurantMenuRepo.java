package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.MenuItem;
import com.foodDelivaryApp.userservice.entity.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantMenuRepo extends JpaRepository<RestaurantMenu , Long> {
    RestaurantMenu findByName(String name);

    @Query(value = "SELECT * FROM `restaurantmenu` WHERE foodCategoryCode =?1" , nativeQuery = true)
    Optional<RestaurantMenu> findByFoodCategoryCode(String foodCategoryCode);

    @Query(value = "SELECT * FROM `menuitem` AS mi LEFT JOIN restaurantmenu rest ON rest.id = mi.menu_id WHERE rest.cuisineType = ?1" , nativeQuery = true)
    List<MenuItem> findByCuisineTypes(String cuisineType);
}
