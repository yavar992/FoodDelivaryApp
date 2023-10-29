package com.foodDelivaryApp.userservice.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class UserRegisterationEvent extends ApplicationEvent {

    public UserRegisterationEvent(Object source) {
        super(source);
    }


    public UserRegisterationEvent(Object source, Clock clock) {
        super(source, clock);
    }

}
