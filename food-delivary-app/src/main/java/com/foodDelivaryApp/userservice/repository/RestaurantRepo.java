package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestaurantRepo extends JpaRepository<Restaurant , Long> {

    @Query(value = "SELECT * FROM restaurant WHERE uniqueIdentifierNumber =?1" , nativeQuery = true)
    Optional<Restaurant> findByUniqueIdentifierNumber(String uniqueIdentifierNumber);
}
