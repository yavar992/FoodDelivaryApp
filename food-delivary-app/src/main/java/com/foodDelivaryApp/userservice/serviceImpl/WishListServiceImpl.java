package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.entity.MenuItem;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.entity.Wishlist;
import com.foodDelivaryApp.userservice.exceptionHandling.MenuItemException;
import com.foodDelivaryApp.userservice.repository.MenuItemRepo;
import com.foodDelivaryApp.userservice.repository.WishlistRepo;
import com.foodDelivaryApp.userservice.service.WishListService;
import com.foodDelivaryApp.userservice.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    private WishlistRepo wishlistRepo;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Override
    public Wishlist addToWishList(Authentication authentication, String foodCode) {
        User user = commonUtil.authenticatedUser(authentication);

        Optional<Wishlist> wishlistOptional = wishlistRepo.findByUserId(user.getId());
        Wishlist wishlist;
        if (wishlistOptional.isPresent()){
            wishlist = wishlistOptional.get();
        }
        else {
            wishlist = new Wishlist();
            wishlist.setUser(user);
            wishlist.setCreatedDate(LocalDateTime.now());
        }
        Optional<MenuItem> menuItem =  menuItemRepo.findByFoodCode(foodCode);
        if (menuItem.isEmpty()){
            throw new MenuItemException("No menu item found for food code " + foodCode);
        }

        List<MenuItem> list = new ArrayList<>();
        list.add(menuItem.get());
        wishlist.setItems(list);
//        wishlist.setFoodCode(menuItem.get().getFoodCode());
        return wishlistRepo.saveAndFlush(wishlist);

    }

    @Override
    public List<Wishlist> getllWishlists(Authentication authentication) {
        User user = commonUtil.authenticatedUser(authentication);
        List<Wishlist> wishlists = wishlistRepo.findAllWishListItemsByUserId(user.getId());
        if (wishlists.isEmpty()){
            throw new MenuItemException("No menuItems found for the user " + user.getFirstName());
        }
        return wishlists;
    }

    @Override
    public String deleteItemFromWishlist(Authentication authentication, String foodCode) {
        commonUtil.authenticatedUser(authentication);

        Optional<Wishlist> wishlistOptional = wishlistRepo.findByFoodCode(foodCode);
        if (wishlistOptional.isEmpty()){
            throw new MenuItemException("No suchItem found in wishlist with the given food code ");
        }
        wishlistRepo.delete(wishlistOptional.get());
        return "item remove successfully from wishList";
    }

    @Override
    public Wishlist wishListItem(Authentication authentication, Long itemId) {
        User user = commonUtil.authenticatedUser(authentication);
        Optional<Wishlist> wishlist = wishlistRepo.findByUserId(user.getId());
        if (wishlist.isEmpty()){
            throw new MenuItemException("No wishlist found for menu item  " + itemId);
        }
        return wishlist.get();
    }

    @Override
    public List<?> getAllWishlistItems() {
       return wishlistRepo.findAll();
    }

    @Override
    public String removeAllItemsFromWishlist(Authentication authentication) {
        List<Wishlist> wishlists = wishlistRepo.findAll();
        if (wishlists.isEmpty()){
            throw new MenuItemException("No item founds in the wishlist !!");
        }
        wishlistRepo.deleteAll();
        return "All Items removed successfully from the wishlist";
    }
}
