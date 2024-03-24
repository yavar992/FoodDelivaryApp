package com.foodDelivaryApp.userservice.event;

import java.time.Clock;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEvent extends ApplicationEvent {

    public RestaurantEvent(Object source) {
        super(source);
    }

    public RestaurantEvent(Object source, Clock clock) {
        super(source, clock);
    }

}
