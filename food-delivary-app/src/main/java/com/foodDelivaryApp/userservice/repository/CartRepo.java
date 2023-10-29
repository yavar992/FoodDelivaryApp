package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Long> {

    Cart findByUserId(Long id);
}
