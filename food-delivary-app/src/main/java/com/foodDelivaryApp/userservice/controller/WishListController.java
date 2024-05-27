package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.entity.Wishlist;
import com.foodDelivaryApp.userservice.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/wishlist")
@RestController
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @PostMapping("/add")
    public ResponseEntity<?> addToWishList(Authentication authentication , @RequestParam("foodCode") String foodCode){
        Wishlist response = wishListService.addToWishList(authentication , foodCode);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<?> viewWishList(Authentication authentication){
        List<Wishlist> wishlists = wishListService.getllWishlists(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(wishlists);
    }

    @DeleteMapping("/remove/{foodCode}")
    public ResponseEntity<?> deleteItemFromWishlist(Authentication authentication , @PathVariable("foodCode") String foodCode){
        String message = wishListService.deleteItemFromWishlist(authentication, foodCode);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> checkWishListItem(Authentication authentication , @PathVariable("itemId") Long itemId){
        Wishlist wishlist = wishListService.wishListItem(authentication, itemId);
        return ResponseEntity.status(HttpStatus.OK).body(wishlist);
    }

    @GetMapping("/allItems")
    public ResponseEntity<?> allWishlistItems(){
        List<?> wishlist = wishListService.getAllWishlistItems();
        return ResponseEntity.status(HttpStatus.OK).body(wishlist);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeAllItemsFromWishlist(Authentication authentication){
        String response = wishListService.removeAllItemsFromWishlist(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

