package com.foodDelivaryApp.userservice.Enums;

public enum DeliveryMethodEnum {

    STANDARD(50.00),          // Standard delivery with a price of $5.00
    EXPRESS(100.00),          // Express delivery with a price of $10.00
    NEXT_DAY(150.00),         // Next day delivery with a price of $15.00
    IN_STORE_PICKUP(40.00),   // In-store pickup with no additional cost
    CURBSIDE_PICKUP(30.50);    // Curbside pickup with a price of $2.50

    // Add more delivery methods as needed

    private final double price;

    DeliveryMethodEnum(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

}
