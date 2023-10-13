package com.foodDelivaryApp.userservice.event;

import com.foodDelivaryApp.userservice.entity.User;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class UserRegisterationEvent extends ApplicationEvent {

    private User user;


    public UserRegisterationEvent(Object source) {
        super(source);
    }


    public UserRegisterationEvent(Object source, Clock clock) {
        super(source, clock);
    }

}
