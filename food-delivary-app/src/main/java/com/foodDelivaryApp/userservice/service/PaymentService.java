package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.entity.OrderDetails;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaymentService {
    OrderDetails checkout(Double price, String usd, String description);

    OrderDetails createOrder(String name, Double price, String description, Double shippingCharge, Double tax, Double totalPrice);

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
