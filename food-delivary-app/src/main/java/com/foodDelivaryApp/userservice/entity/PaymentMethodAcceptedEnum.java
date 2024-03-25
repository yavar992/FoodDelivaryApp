package com.foodDelivaryApp.userservice.entity;

public enum PaymentMethodAcceptedEnum {

    CREDIT_CARD,
    DEBIT_CARD,
    NET_BANKING,
    UPI,
    PAYTM,
    PHONEPE,
    GOOGLE_PAY,
    STRIPE,
    PAYPAL,
    CASH_ON_DELIVERY,
    DIGITAL_CURRENCIES ;

    public static PaymentMethodAcceptedEnum fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid PaymentMethodAccepted " + value);
        }
    }
}
