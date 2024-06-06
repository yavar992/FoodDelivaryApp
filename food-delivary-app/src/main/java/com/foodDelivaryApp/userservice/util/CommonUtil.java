package com.foodDelivaryApp.userservice.util;

import com.foodDelivaryApp.userservice.entity.CartItem;
import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.exceptionHandling.DeliveryGuyException;
import com.foodDelivaryApp.userservice.exceptionHandling.MenuItemException;
import com.foodDelivaryApp.userservice.exceptionHandling.UserNotFoundException;
import com.foodDelivaryApp.userservice.repository.CartItemRepo;
import com.foodDelivaryApp.userservice.repository.DeliveryGuyRepo;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.DeliveryGuyService;
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
    private UserRepo userRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private DeliveryGuyRepo deliveryGuyRepo;


    String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public User authenticatedUser(Authentication authentication){
        String username = authentication.getName();
        User user =  userRepo.findUserByEmail(username);
        if (user==null){
            throw new UserNotFoundException("No user found  with email " + username);
        }
        return user;
    }

    public DeliveryGuy authenticateDeliveryGuy(Authentication authentication){
        String username = authentication.getName();
        DeliveryGuy deliveryGuy =  deliveryGuyRepo.findByEmail(username);
        if (deliveryGuy==null){
            throw new DeliveryGuyException("No deliveryGuy found for " + username);
        }
        return deliveryGuy;

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


