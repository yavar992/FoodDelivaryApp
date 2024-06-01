package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.entity.OrderDetails;
import com.foodDelivaryApp.userservice.Enums.PaymentIntentEnum;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.repository.OrderDetailsRepo;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.PaymentService;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private APIContext apiContext;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public OrderDetails checkout(Double price, String usd, String description) {
        return null;
    }

    @Override
    public OrderDetails createOrder(String name, Double price, String description, Double shippingCharge, Double tax, Double totalPrice) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setDescription(description);
        orderDetails.setIntent(PaymentIntentEnum.INITIATE);
        orderDetails.setTax(tax);
        orderDetails.setTotal(totalPrice);
        orderDetails.setPrice(price);
        orderDetails.setCreatedTime(LocalDateTime.now());
        orderDetails.setProductName(name);
        orderDetails.setShippingCharge(shippingCharge);
        String UUID = java.util.UUID.randomUUID().toString();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepo.findByEmail(userDetails.getUsername());
        orderDetails.setUser(user);
        orderDetails.setReferenceNumber(UUID);

        return orderDetailsRepo.saveAndFlush(orderDetails);
    }

    public Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }

}
