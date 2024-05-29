package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.WishlistDTO;
import com.foodDelivaryApp.userservice.DTO.WishlistResponseDTO;
import com.foodDelivaryApp.userservice.entity.Wishlist;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WishListService {
    WishlistResponseDTO addToWishList(Authentication authentication, String foodCode);

    List<WishlistDTO> getllWishlists(Authentication authentication);

    String deleteItemFromWishlist(Authentication authentication, String foodCode);

    WishlistDTO wishListItem(Authentication authentication, Long itemId);

    List<?> getAllWishlistItems();

    String removeAllItemsFromWishlist(Authentication authentication);

    WishlistDTO getWishlistItemByFoodCode(Authentication authentication, String foodCode);

    String moveItemToCartfFromWishlist(Authentication authentication, String foodCode);
}
