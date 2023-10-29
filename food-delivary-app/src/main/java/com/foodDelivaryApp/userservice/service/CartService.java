package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.CartDTO;
import com.foodDelivaryApp.userservice.entity.User;

public interface CartService {
    String addToCart(Long itemId , Long quantity);

    User currentLoginUserDetails();


    CartDTO getCartById(Long id);

    CartDTO getCartByUserId(Long id);

    String updateCartQuantity(Long id, Long quantity);

    String deleteCart(Long id);
}
