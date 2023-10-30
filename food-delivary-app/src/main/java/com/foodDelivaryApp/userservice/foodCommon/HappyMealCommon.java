package com.foodDelivaryApp.userservice.foodCommon;

import com.foodDelivaryApp.userservice.entity.MenuItem;
import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.exceptionHandling.InvalidRestaurantException;
import com.foodDelivaryApp.userservice.exceptionHandling.MenuItemException;
import com.foodDelivaryApp.userservice.exceptionHandling.UnverifiedUserException;
import com.foodDelivaryApp.userservice.repository.MenuItemRepo;
import com.foodDelivaryApp.userservice.repository.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HappyMealCommon {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private MenuItemRepo menuItemRepo;


    public static void isUserVerified(Boolean isVerified){
        if (!isVerified){
            throw new UnverifiedUserException("");
        }
    }

    public Restaurant findRestaurant(String uniqueIdentifierNumber){
        Optional<Restaurant> restaurant = restaurantRepo.findByUniqueIdentifierNumber(uniqueIdentifierNumber);
        if (restaurant.isEmpty()){
            throw new InvalidRestaurantException("No restaurant found for the uniqueIdentifierNumber " + uniqueIdentifierNumber);
        }
        return restaurant.get();
    }

    public MenuItem findMenuItemByFoodCode(String foodCode){
        Optional<MenuItem> menuItem = menuItemRepo.findByFoodCode(foodCode);
        if (menuItem.isEmpty()){
            throw new MenuItemException("No item found for the foodCode "+ foodCode);
        }
        return menuItem.get();
    }

    public MenuItem findMealItemById(Long id){
        return menuItemRepo.findById(id).orElseThrow(()-> new MenuItemException("No item found for the id "+ id));

    }
}



