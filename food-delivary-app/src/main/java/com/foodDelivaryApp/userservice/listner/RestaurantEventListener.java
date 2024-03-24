package com.foodDelivaryApp.userservice.listner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.event.RestaurantEvent;
import com.foodDelivaryApp.userservice.util.IEmailSender;

@Service
public class RestaurantEventListener {

    @Autowired
    private IEmailSender emailSender;

    @Async
    @EventListener
    public void onApplicationEvent(RestaurantEvent event) {
        if (event.getSource() instanceof Restaurant) {
            Restaurant restaurant = (Restaurant) event.getSource();
            String restaurantName = restaurant.getName();
            
            emailSender.sendEmail(restaurant.getRestaurantOwner().getEmail() , 
                    "welcome.txt", "Welcome to HappyMeal");
        }

    }
}
