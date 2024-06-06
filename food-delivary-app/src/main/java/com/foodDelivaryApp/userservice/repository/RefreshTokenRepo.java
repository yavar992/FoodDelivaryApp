package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.jwt.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {


    Optional<RefreshToken> findByToken(String token);

    @Query(value = "SELECT * FROM `refreshtoken` WHERE user_id = ?1",nativeQuery = true)
    RefreshToken findByUserId(Long id);

    @Query(value = "SELECT * FROM `refreshtoken` WHERE restaurantOwner_id = ?1", nativeQuery = true)
    RefreshToken findByRestaurantOwnerId(Long id);

    @Query(value = "SELECT * FROM `refreshtoken` WHERE deliveryGuy_id = ?1", nativeQuery = true)
    RefreshToken findByDeliveryBoyId(Long id);
}
