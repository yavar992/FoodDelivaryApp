package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WishlistRepo extends JpaRepository<Wishlist , Long> {

    @Query(value = "SELECT * FROM `wishlist` WHERE user_id = ?1" , nativeQuery = true)
    Optional<Wishlist> findByUserId(Long id);

    @Query(value = "SELECT * FROM `wishlist` WHERE user_id = ?1" , nativeQuery = true)
    List<Wishlist> findAllWishListItemsByUserId(Long id);

    Optional<Wishlist> findByFoodCode(String foodCode);
}
