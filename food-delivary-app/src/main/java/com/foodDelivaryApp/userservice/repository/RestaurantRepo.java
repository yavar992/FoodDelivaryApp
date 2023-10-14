package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepo extends JpaRepository<Restaurant , Long> {
}
