package com.foodDelivaryApp.userservice.entity;

public enum CheckoutStatusEnum {

    INITIATED,
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED,
    REFUNDED,
    CHARGEBACK,
    EXPIRED,
    ON_HOLD
}
