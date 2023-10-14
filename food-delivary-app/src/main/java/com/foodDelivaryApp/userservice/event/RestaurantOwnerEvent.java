package com.foodDelivaryApp.userservice.event;

import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class RestaurantOwnerEvent extends ApplicationEvent {

    private RestaurantOwner restaurantOwner;

    public RestaurantOwnerEvent(Object source) {
        super(source);
    }

    public RestaurantOwnerEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
