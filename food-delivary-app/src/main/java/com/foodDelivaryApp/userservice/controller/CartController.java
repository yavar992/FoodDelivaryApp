package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.CartDTO;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealConstant;
import com.foodDelivaryApp.userservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{itemId}")
    public ResponseEntity<?> addToCart(@PathVariable("itemId") Long itemId , @RequestParam(value = "quantity") Long quantity){
        try {
            String addToCartMessage = cartService.addToCart(itemId,quantity);
            if (addToCartMessage!=null){
                return ResponseEntity.status(HttpStatus.CREATED).body(addToCartMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @GetMapping("/loginUser") // getting the current login user
    private ResponseEntity<?> getCurrentLoginUser(){
        User user =  cartService.currentLoginUserDetails();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getCartById(@PathVariable ("id") Long id){
        try {
            CartDTO cartDTO = cartService.getCartById(id);
            if (cartDTO!=null){
                return ResponseEntity.status(HttpStatus.OK).body(cartDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @GetMapping("cartByUserId/{id}")
    public ResponseEntity<?> getCartByUserId(@PathVariable ("id") Long id){
        try {
            CartDTO cartDTO = cartService.getCartByUserId(id);
            if (cartDTO!=null){
                return ResponseEntity.status(HttpStatus.OK).body(cartDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @GetMapping("/updateCart/{id}")
    public ResponseEntity<?> updateCartQuantity(@PathVariable ("id") Long id , @RequestParam("quantity") Long quantity){
        try {
            String cartDTO = cartService.updateCartQuantity(id,quantity);
            if (cartDTO!=null){
                return ResponseEntity.status(HttpStatus.OK).body(cartDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @GetMapping("/deleteCart/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable ("id") Long id){
        try {
            String cartDTO = cartService.deleteCart(id);
            if (cartDTO!=null){
                return ResponseEntity.status(HttpStatus.OK).body(cartDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }










}
