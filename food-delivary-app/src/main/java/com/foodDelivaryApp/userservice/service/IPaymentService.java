package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.entity.OrderDetails;

public interface IPaymentService {
    OrderDetails checkout(Double price, String usd, String description);

    OrderDetails createOrder(String name, Double price, String description, Double shippingCharge, Double tax, Double totalPrice);
}
