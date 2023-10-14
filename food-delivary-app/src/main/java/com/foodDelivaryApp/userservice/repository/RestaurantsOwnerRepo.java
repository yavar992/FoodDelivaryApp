package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.DTO.VerifyOTP;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestaurantsOwnerRepo extends JpaRepository<RestaurantOwner , Long> {
    Optional<RestaurantOwner> findByEmail(String email);


    @Query(name ="SELECT * FROM `restaurantowner` WHERE otp = ?1" , nativeQuery = true)
    RestaurantOwner findByOtp(Integer otp);
}
