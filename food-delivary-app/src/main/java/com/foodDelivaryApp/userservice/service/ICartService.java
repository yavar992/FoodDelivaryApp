package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.dto.CartDTO;
import com.foodDelivaryApp.userservice.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICartService {

    String addToCart(Long itemId , Long quantity , Authentication authentication);

    User currentLoginUserDetails();

    CartDTO getCartById(Long id);

    CartDTO getCartByUserId(Long id);

    String updateCartQuantity(Long id , Long quantity , Authentication authentication);

    String deleteCart(Long id );

    List<CartDTO> getAllCartItem(Authentication authentication);
}
