package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant , Long> {

    @Query("SELECT r FROM Restaurant r WHERE r.uniqueIdentifierNumber = :identifier")
    Optional<Restaurant> findByUniqueIdentifierNumber(@Param("identifier") String identifier);

    @Query("SELECT r FROM Restaurant r WHERE r.email = :email")
    Optional<Restaurant> findByEmail(@Param("email") String email);
}
