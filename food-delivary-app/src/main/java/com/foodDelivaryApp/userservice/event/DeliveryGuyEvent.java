package com.foodDelivaryApp.userservice.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class DeliveryGuyEvent extends ApplicationEvent {

    public DeliveryGuyEvent(Object source) {
        super(source);
    }

    public DeliveryGuyEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
