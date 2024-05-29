package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.DTO.WishlistDTO;
import com.foodDelivaryApp.userservice.DTO.WishlistResponseDTO;
import com.foodDelivaryApp.userservice.convertor.CartConvertor;
import com.foodDelivaryApp.userservice.entity.*;
import com.foodDelivaryApp.userservice.exceptionHandling.MenuItemException;
import com.foodDelivaryApp.userservice.repository.CartItemRepo;
import com.foodDelivaryApp.userservice.repository.CartRepo;
import com.foodDelivaryApp.userservice.repository.MenuItemRepo;
import com.foodDelivaryApp.userservice.repository.WishlistRepo;
import com.foodDelivaryApp.userservice.service.WishListService;
import com.foodDelivaryApp.userservice.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    private WishlistRepo wishlistRepo;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartRepo cartRepo;


    @Override
    public WishlistResponseDTO addToWishList(Authentication authentication, String foodCode) {
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
            wishlist.setItems(new ArrayList<>()); // Initialize the items list
        }
        Optional<MenuItem> menuItem =  menuItemRepo.findByFoodCode(foodCode);
        if (menuItem.isEmpty()){
            throw new MenuItemException("No menu item found for food code " + foodCode);
        }

        // Add the new menu item to the existing list of items
        List<MenuItem> items = wishlist.getItems();
        if (!items.contains(menuItem.get())) {
            items.add(menuItem.get());
        }
        wishlist.setUpdatedDate(LocalDateTime.now());

        wishlistRepo.saveAndFlush(wishlist);
        return convertWishlistToWishlistResponseDTO(wishlist ,menuItem.get());
    }

    @Override
    public List<WishlistDTO> getllWishlists(Authentication authentication) {
        User user = commonUtil.authenticatedUser(authentication);
        Optional<Wishlist> wishlistOptional = wishlistRepo.findByUserId(user.getId());

        if (wishlistOptional.isPresent()) {
            Wishlist wishlist = wishlistOptional.get();
            List<MenuItem> items = wishlist.getItems();

            return items.stream()
                    .map(this::convertToWishlistDTO)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList(); // Return empty list if no wishlist found
        }

    }

    @Override
    public String deleteItemFromWishlist(Authentication authentication, String foodCode) {
        User user = commonUtil.authenticatedUser(authentication);
        Optional<Wishlist> wishlistOptional = wishlistRepo.findByUserId(user.getId());

        if (wishlistOptional.isPresent()) {
            Wishlist wishlist = wishlistOptional.get();
            Optional<MenuItem> menuItemOptional = menuItemRepo.findByFoodCode(foodCode);

            if (menuItemOptional.isPresent()) {
                MenuItem menuItem = menuItemOptional.get();
                wishlist.getItems().remove(menuItem);
                wishlist.setUpdatedDate(LocalDateTime.now());
                wishlistRepo.saveAndFlush(wishlist);

            } else {
                throw new MenuItemException("No menu item found for food code " + foodCode);
            }
        }
        else {
            throw new MenuItemException("No wishlist found for user " + user.getUsername());
        }
        return "Item successfully removed from wishlist";
    }


    @Override
    public WishlistDTO wishListItem(Authentication authentication, Long itemId) {
        User user = commonUtil.authenticatedUser(authentication);
        Optional<Wishlist> wishlist = wishlistRepo.findByUserId(user.getId());
        if (wishlist.isEmpty()){
            throw new MenuItemException("No wishlist found for menu item  " + itemId);
        }
        Wishlist wishlist1 = wishlist.get();
        List<MenuItem> menuItems = wishlist1.getItems();
        Optional<MenuItem> itemOpt = menuItems.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst();

        if (itemOpt.isEmpty()) {
            throw new MenuItemException("Menu item with id " + itemId + " not found in the wishlist");
        }

        return convertToWishlistDTO(itemOpt.get());
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

    @Override
    public WishlistDTO getWishlistItemByFoodCode(Authentication authentication, String foodCode) {

        User user = commonUtil.authenticatedUser(authentication);
        Optional<Wishlist> wishlistOpt = wishlistRepo.findByUserId(user.getId());

        if (wishlistOpt.isEmpty()) {
            throw new MenuItemException("No wishlist found for user " + user.getId());
        }

        Wishlist wishlist = wishlistOpt.get();
        List<MenuItem> menuItems = wishlist.getItems();

        Optional<MenuItem> itemOpt = menuItems.stream()
                .filter(item -> item.getFoodCode().equals(foodCode))
                .findFirst();

        if (itemOpt.isEmpty()) {
            throw new MenuItemException("Menu item with food code " + foodCode + " not found in the wishlist");
        }

        return convertToWishlistDTO(itemOpt.get());
    }

    @Override
    public String moveItemToCartfFromWishlist(Authentication authentication, String foodCode) {
        User user = commonUtil.authenticatedUser(authentication);
        Optional<Wishlist> wishlistOpt = wishlistRepo.findByUserId(user.getId());

        if (wishlistOpt.isEmpty()) {
            throw new MenuItemException("No wishlist found for user " + user.getId());
        }

        Wishlist wishlist = wishlistOpt.get();
        List<MenuItem> menuItems = wishlist.getItems();

        Optional<MenuItem> itemOpt = menuItems.stream()
                .filter(item -> item.getFoodCode().equals(foodCode))
                .findFirst();

        if (itemOpt.isEmpty()) {
            throw new MenuItemException("Menu item with food code " + foodCode + " not found in the wishlist");
        }
        MenuItem menuItem = itemOpt.get();
        //adding the item to the cartItem
        CartItem cartItem = CartConvertor.convertMenuItemToCart(menuItem);
        cartItem.setMenuItem(menuItem);
        cartItem.setUserId(user.getId());
        cartItem.setMenuItemId(menuItem.getId());
        cartItem.setQuantity(1L);
        commonUtil.checkingIfItemIsAlreadyInCart(menuItem.getId() , cartItem);

        //adding the details of the cartItem into the cart
        Cart cart = cartRepo.findByUserId(user.getId());
        if (cart==null){
            cart.setUser(user);
            cart.setQuantityOfCartItem(1L);
            cart.setTotalAmount(Math.round(menuItem.getPrice()));
        }
        cart.setTotalAmount(Math.round(cart.getTotalAmount()+menuItem.getPrice()));
        cart.setUser(user);
        cart.setQuantityOfCartItem(cart.getQuantityOfCartItem()+1);
        cartItem.setCart(cart);
        cartItemRepo.saveAndFlush(cartItem);
        cartRepo.saveAndFlush(cart);

        //removing the item from the wishlist once the item added into the cart
        List<MenuItem> menuItems1 = wishlist.getItems();
        menuItems1.remove(menuItem);
        wishlistRepo.saveAndFlush(wishlist);
        return "Item Successfully Added to the cart";


    }


    private WishlistDTO convertToWishlistDTO(MenuItem menuItem) {
        return WishlistDTO.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .foodCode(menuItem.getFoodCode())
                .build();
    }

    private WishlistResponseDTO convertWishlistToWishlistResponseDTO(Wishlist wishlist, MenuItem menuItem){
        WishlistDTO wishlistDTO = convertToWishlistDTO(menuItem);
        return WishlistResponseDTO.builder()
                .id(wishlist.getId())
                .wishlistDTO(wishlistDTO)
                .build();
    }


}
