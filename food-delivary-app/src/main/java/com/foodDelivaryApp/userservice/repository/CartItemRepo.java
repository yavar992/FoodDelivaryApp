package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Cart;
import com.foodDelivaryApp.userservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    @Query(value = "SELECT * FROM `cart` WHERE user_id = ?1" ,nativeQuery = true)
    CartItem findByUserId(Long id);


    @Query(value = "SELECT * FROM `cart` WHERE user_id = ?1" ,nativeQuery = true)
    List<CartItem> findByUserIds(Long id);
}
