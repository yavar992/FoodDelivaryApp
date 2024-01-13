package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepo extends JpaRepository<Cart , Long> {

    @Query(value = " SELECT totalAmount FROM `cart` WHERE user_id = ?1" , nativeQuery = true)
    Long findAmountOfCart(Long id);

    @Query(value = "SELECT quantityOfCartItem FROM `cart` WHERE user_id = ?1 " , nativeQuery = true)
    Long findQuantityOfCartItem(Long id);

    @Query(value = "SELECT * FROM `cart` WHERE user_id = ?1 " , nativeQuery = true)
    Cart findByUserId(Long id);
}
