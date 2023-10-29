package com.foodDelivaryApp.userservice.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class RestaurantOwnerEvent extends ApplicationEvent {

    public RestaurantOwnerEvent(Object source) {
        super(source);
    }

    public RestaurantOwnerEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
