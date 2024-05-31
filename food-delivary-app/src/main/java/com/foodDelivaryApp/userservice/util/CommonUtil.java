package com.foodDelivaryApp.userservice.util;

import com.foodDelivaryApp.userservice.entity.CartItem;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.exceptionHandling.MenuItemException;
import com.foodDelivaryApp.userservice.repository.CartItemRepo;
import com.foodDelivaryApp.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
public class CommonUtil {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepo cartItemRepo;

    String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public User authenticatedUser(Authentication authentication){
        String username = authentication.getName();
        return userService.findUserByEmail(username);
    }


    public void checkingIfItemIsAlreadyInCart(Long id , CartItem cartItems) {
        Optional<CartItem> cartItem = cartItemRepo.findByMenuItemId(id);
        if (cartItem.isPresent()){
            log.info("cartItem {} , " , cartItem.get());
//            throw new MenuItemException("Item is already exists in cart");
            cartItems.setQuantity(cartItems.getQuantity()+1);
            cartItems.setUpdateAt(LocalDateTime.now());
        }
    }

    public void generateOrderNumber(){

    }
}


