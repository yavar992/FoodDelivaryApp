package com.foodDelivaryApp.userservice.event;

import org.springframework.context.ApplicationEvent;

public class DeliveryGuyEvent extends ApplicationEvent {
    public DeliveryGuyEvent(Object source) {
        super(source);
    }
}
