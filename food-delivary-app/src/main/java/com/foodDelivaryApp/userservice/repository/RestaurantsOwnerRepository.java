package com.foodDelivaryApp.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foodDelivaryApp.userservice.entity.RestaurantOwner;

public interface RestaurantsOwnerRepository extends JpaRepository<RestaurantOwner , Long> {

    @Query("SELECT * FROM `restaurantowner` WHERE otp = :otp")
    RestaurantOwner findByOtp(@Param("otp")int otp);

    @Query(value = "SELECT r FROM RestaurantOwner r WHERE r.email = :email")
    Optional<RestaurantOwner> findByUsernameOrEmail(@Param("email") String email);

    @Query(value = "SELECT r FROM RestaurantOwner r WHERE r.email = :email")
    Optional<RestaurantOwner> findByEmail(@Param("email") String email);
}
