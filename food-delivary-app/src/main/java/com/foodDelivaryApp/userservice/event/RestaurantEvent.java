package com.foodDelivaryApp.userservice.event;

import com.foodDelivaryApp.userservice.entity.Restaurant;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class RestaurantEvent extends ApplicationEvent {

    private Restaurant restaurant;

    public RestaurantEvent(Object source) {
        super(source);
    }

    public RestaurantEvent(Object source, Clock clock) {
        super(source, clock);
    }

}
