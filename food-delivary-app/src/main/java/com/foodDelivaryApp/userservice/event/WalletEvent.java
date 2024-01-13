package com.foodDelivaryApp.userservice.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class WalletEvent extends ApplicationEvent {
    public WalletEvent(Object source) {
        super(source);
    }

    public WalletEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
