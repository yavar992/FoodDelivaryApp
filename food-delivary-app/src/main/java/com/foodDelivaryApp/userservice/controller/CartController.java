package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.CartDTO;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealConstant;
import com.foodDelivaryApp.userservice.service.CartService;
import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{itemId}")
    public ResponseEntity<?> addToCart(@PathVariable("itemId") Long itemId , @RequestParam(value = "quantity") Long quantity ,
                                       Authentication authentication){
        try {
            String addToCartMessage = cartService.addToCart(itemId,quantity , authentication);
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

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateCartQuantity(@PathVariable("id") Long id , @RequestParam("quantity") Long quantity ,
                                                Authentication authentication){
        try {
            String cartDTO = cartService.updateCartQuantity(id , quantity , authentication);
            if (cartDTO!=null){
                return ResponseEntity.status(HttpStatus.OK).body(cartDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable ("id") Long id ){
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

    //GET ALL THE CART ITEM OF A USER

    @GetMapping
    public ResponseEntity<?> getAllCartItem(Authentication authentication){
        List<CartDTO> cartDTOS = cartService.getAllCartItem(authentication);
        if (cartDTOS.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No cartItem found ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(cartDTOS);
    }










}
