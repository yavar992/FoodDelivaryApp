package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.entity.Wishlist;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WishListService {
    Wishlist addToWishList(Authentication authentication, String foodCode);

    List<Wishlist> getllWishlists(Authentication authentication);

    String deleteItemFromWishlist(Authentication authentication, String foodCode);

    Wishlist wishListItem(Authentication authentication, Long itemId);

    List<?> getAllWishlistItems();

    String removeAllItemsFromWishlist(Authentication authentication);
}
