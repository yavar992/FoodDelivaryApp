package com.foodDelivaryApp.userservice.event;

import org.springframework.context.ApplicationEvent;

public class OrderConfirmationDetailsEvent extends ApplicationEvent {
    public OrderConfirmationDetailsEvent(Object source) {
        super(source);
    }
}
