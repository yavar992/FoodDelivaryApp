package com.foodDelivaryApp.userservice.listner;

import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.event.RestaurantEvent;
import com.foodDelivaryApp.userservice.util.EmailSendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantEventListener {

//    implements ApplicationListener<RestaurantEvent>

    @Autowired
    EmailSendarUtil emailSendarUtil;

    @Async
    @EventListener
    public void onApplicationEvent(RestaurantEvent event) {
        Restaurant restaurant = (Restaurant) event.getSource();
        String email = restaurant.getRestaurantOwner().getEmail();
        String restaurantName = restaurant.getName();

        List<String> body = new ArrayList<>();
        body.add("Dear " + restaurantName + " " );
        body.add("Congratulations on successfully adding your restaurant to HappyMeal!");
        body.add("We are excited to have you on our platform and look forward to helping you grow your business.");
        body.add("");
        body.add("Your restaurant will now be featured on our website and app, where customers will be able to browse your menu, place orders, and leave reviews.");
        body.add("");
        body.add("We will also be sending out an email blast to our customers announcing your new addition to HappyMeal.");
        body.add("");
        body.add("To get started, please log in to your HappyMeal restaurant portal at happymeal/8282/login. From there, you can update your restaurant information, manage your menu, and process orders.");
        body.add("");
        body.add("If you have any questions or need assistance, please do not hesitate to contact us at happyMeal123@gmail.com");
        body.add("");
        body.add("We look forward to working with you to make HappyMeal the best food delivery service for restaurants and customers alike.");
        body.add("");
        body.add("Sincerely,");
        body.add("The HappyMeal Team");
        emailSendarUtil.sendEmailWithMultipleBodyLine(email , body, "Welcome to HappyMeal");
    }
}
