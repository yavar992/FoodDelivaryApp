package com.foodDelivaryApp.userservice.foodCommon;

import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.exceptionHandling.InvalidRestaurantException;
import com.foodDelivaryApp.userservice.exceptionHandling.UnverifiedUserException;
import com.foodDelivaryApp.userservice.repository.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HappyMealCommon {

    @Autowired
    private RestaurantRepo restaurantRepo;


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
}



