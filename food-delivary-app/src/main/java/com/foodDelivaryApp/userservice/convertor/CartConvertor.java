package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.DTO.CartDTO;
import com.foodDelivaryApp.userservice.entity.Cart;
import com.foodDelivaryApp.userservice.entity.CartItem;
import com.foodDelivaryApp.userservice.entity.MenuItem;

import java.time.LocalDateTime;

public interface CartConvertor {

    static CartItem convertMenuItemToCart(MenuItem menuItem){
        return CartItem.builder()
                .price(menuItem.getPrice())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
    }

    static CartDTO convertCartToCartDTO(CartItem cart){
        return CartDTO.builder()
                .name(cart.getName())
                .price(cart.getPrice())
                .description(cart.getDescription())
                .build();
    }


}
