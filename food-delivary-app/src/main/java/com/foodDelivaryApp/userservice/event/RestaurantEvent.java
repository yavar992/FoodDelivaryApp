package com.foodDelivaryApp.userservice.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class RestaurantEvent extends ApplicationEvent {

    public RestaurantEvent(Object source) {
        super(source);
    }

    public RestaurantEvent(Object source, Clock clock) {
        super(source, clock);
    }

}
