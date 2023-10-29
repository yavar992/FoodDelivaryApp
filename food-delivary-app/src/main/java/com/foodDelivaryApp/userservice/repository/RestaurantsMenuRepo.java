package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantsMenuRepo extends JpaRepository<RestaurantMenu ,  Long> {
}
