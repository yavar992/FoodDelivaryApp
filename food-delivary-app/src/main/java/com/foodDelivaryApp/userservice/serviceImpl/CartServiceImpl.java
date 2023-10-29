package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.DTO.CartDTO;
import com.foodDelivaryApp.userservice.convertor.CartConvertor;
import com.foodDelivaryApp.userservice.entity.Cart;
import com.foodDelivaryApp.userservice.entity.MenuItem;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.exceptionHandling.CartException;
import com.foodDelivaryApp.userservice.exceptionHandling.MenuItemException;
import com.foodDelivaryApp.userservice.exceptionHandling.UserNotFoundException;
import com.foodDelivaryApp.userservice.repository.CartRepo;
import com.foodDelivaryApp.userservice.repository.MenuItemRepo;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Override
    public String addToCart(Long itemId, Long quantity) {
        MenuItem menuItem = menuItemRepo.findById(itemId).orElseThrow(() -> new MenuItemException("No Menu Item found for id " + itemId));
        log.info("menuItems {}" ,menuItem);
        Cart cart = CartConvertor.convertMenuItemToCart(menuItem);
        if (quantity==null){
            quantity = 1L;
        }
        cart.setQuantity(quantity);
        cart.setPrice(quantity * cart.getPrice());
        cart.setMenuItemId(menuItem.getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepo.findByEmail(userDetails.getUsername());
        cart.setUser(user);
        cartRepo.save(cart);
        user.setCart(cart);
        userRepo.saveAndFlush(user);
        return "Item successfully added to cart !!";
    }


    @Override
    public User currentLoginUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user =  userRepo.findByEmail(userDetails.getUsername());
        log.info("user {}",user);
        return user;
    }

    @Override
    public CartDTO getCartById(Long id) {
        Cart cart = cartRepo.findById(id).orElseThrow(()-> new CartException("Cart not found for id " + id));
        return CartConvertor.convertCartToCartDTO(cart);
    }

    @Override
    public CartDTO getCartByUserId(Long id) {
         userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("User not found for user id  " + id ));
        Cart cart = cartRepo.findByUserId(id);
        if (cart==null){
            throw new CartException("No cart found for the userId " + id);
        }
        return CartConvertor.convertCartToCartDTO(cart);

    }

    @Override
    public String updateCartQuantity(Long id, Long quantity) {
        Cart cart = cartRepo.findById(id).orElseThrow(()-> new CartException(" Cart not found for the id " + id));
        cart.setQuantity(quantity);
        cart.setPrice(cart.getPrice()*quantity);
        cartRepo.saveAndFlush(cart);
        return "cart updated successfully !!";
    }

    @Override
    public String deleteCart(Long id) {
        Cart cart = cartRepo.findById(id).orElseThrow(()-> new CartException(" Cart not found for the id " + id));
        cartRepo.delete(cart);
        return "cart deleted successfully !";
    }

}