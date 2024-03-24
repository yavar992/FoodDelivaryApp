package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.convertor.CartConvertor;
import com.foodDelivaryApp.userservice.dto.CartDTO;
import com.foodDelivaryApp.userservice.entity.Cart;
import com.foodDelivaryApp.userservice.entity.CartItem;
import com.foodDelivaryApp.userservice.entity.MenuItem;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.exceptionHandling.CartException;
import com.foodDelivaryApp.userservice.exceptionHandling.MenuItemException;
import com.foodDelivaryApp.userservice.exceptionHandling.UserNotFoundException;
import com.foodDelivaryApp.userservice.repository.CartItemRepo;
import com.foodDelivaryApp.userservice.repository.CartRepo;
import com.foodDelivaryApp.userservice.repository.MenuItemRepo;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CartService implements ICartService {


    @Autowired
    private UserRepo userRepo;


    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Autowired
    private CartRepo cartRepo;





    @Override
    public String addToCart(Long itemId, Long quantity , Authentication authentication) {
        String email = authentication.getName();
        User user = userRepo.findByEmail(email);
        MenuItem menuItem = menuItemRepo.findById(itemId).orElseThrow(() -> new MenuItemException("No Menu Item found for id " + itemId));
        log.info("menuItems {}" ,menuItem);
        CartItem cart = CartConvertor.convertMenuItemToCart(menuItem);
        if (quantity==null){
            quantity = 1L;
        }
        cart.setQuantity(quantity);
        double price = quantity* cart.getPrice();
        cart.setPrice(price);
        cart.setMenuItemId(menuItem.getId());
        cart.setMenuItem(menuItem);
        cart.setUserId(user.getId());

        Cart cart1 = cartRepo.findByUserId(user.getId());
        if (cart1 != null) {
            cart1.setQuantityOfCartItem(cart1.getQuantityOfCartItem() + quantity);
            cart1.setTotalAmount((long) (cart1.getTotalAmount() + price));
        } else {
            cart1 = new Cart();
            cart1.setUser(user);
            cart1.setQuantityOfCartItem(quantity);
            cart1.setTotalAmount((long) price);
        }
        cart.setCart(cart1);
        cartRepo.saveAndFlush(cart1);
        cartItemRepo.saveAndFlush(cart);

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
        CartItem cart = cartItemRepo.findById(id).orElseThrow(()-> new CartException("Cart not found for id " + id));
        return CartConvertor.convertCartToCartDTO(cart);
    }

    @Override
    public CartDTO getCartByUserId(Long id) {
         userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("User not found for user id  " + id ));
        CartItem cart = cartItemRepo.findByUserId(id);
        if (cart==null){
            throw new CartException("No cart found for the userId " + id);
        }
        return CartConvertor.convertCartToCartDTO(cart);

    }

    @Override
    public String updateCartQuantity(Long id , Long quantity , Authentication authentication) {
        String email = authentication.getName();
        User user = userRepo.findByEmail(email);
        CartItem cart = cartItemRepo.findById(id).orElseThrow(()-> new CartException("No card found for id " + id));
        cart.setQuantity(quantity);
        cart.setPrice(cart.getPrice()*quantity);
        cartItemRepo.saveAndFlush(cart);

        List<CartItem> cartItems = cartItemRepo.findByUserIds(user.getId());
        long quantityy = 0;
        long amount = 0;
        Cart cart1 = cartRepo.findByUserId(user.getId());
        for (CartItem cartItem : cartItems) {
            quantityy =  quantityy +   cartItem.getQuantity();
            amount = (long) (amount + cartItem.getPrice());
        }
        cart1.setQuantityOfCartItem(quantityy);
        cart1.setTotalAmount(amount);
        cartRepo.saveAndFlush(cart1);

        return "cart updated successfully !!";

    }

    @Override
    public String deleteCart(Long id ) {
        CartItem cart = cartItemRepo.findById(id).orElseThrow(()-> new CartException(" Cart not found for the id " + id));
        Cart cart1 = cartRepo.findByUserId(cart.getUserId());
        cart1.setTotalAmount((long) (cart1.getTotalAmount() - cart.getPrice()));
        cart1.setQuantityOfCartItem(cart1.getQuantityOfCartItem() - cart.getQuantity());
        cartItemRepo.delete(cart);
        cartRepo.saveAndFlush(cart1);
        return "cart deleted successfully !";
    }

    @Override
    public List<CartDTO> getAllCartItem(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepo.findByEmail(email);
        List<CartItem> cartItems = cartItemRepo.findByUserIds(user.getId());
        log.info("cartItems: " + cartItems);
        return cartItems.stream().map(CartConvertor::convertCartToCartDTO).collect(Collectors.toList());
    }

}