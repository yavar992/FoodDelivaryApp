package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.RestaurantMenu;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantsMenuRepo extends JpaRepository<RestaurantMenu ,  Long> {
}
