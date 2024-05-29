package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Cart;
import com.foodDelivaryApp.userservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    @Query(value = "SELECT * FROM `cartitem` WHERE userId = ?1" ,nativeQuery = true)
    CartItem findByUserId(Long id);


    @Query(value = "SELECT * FROM `cartitem` WHERE userId = ?1" ,nativeQuery = true)
    List<CartItem> findByUserIds(Long id);

    @Query(value = "SELECT * FROM `cartitem` WHERE menuItemId =?1", nativeQuery = true)
    Optional<CartItem> findByMenuItemId(Long id);
}












