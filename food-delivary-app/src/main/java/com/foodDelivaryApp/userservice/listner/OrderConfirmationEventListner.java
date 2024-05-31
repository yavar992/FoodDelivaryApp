package com.foodDelivaryApp.userservice.listner;

import com.foodDelivaryApp.userservice.entity.Address;
import com.foodDelivaryApp.userservice.entity.OrderConfirmationDetails;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.event.OrderConfirmationDetailsEvent;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.util.EmailSendarUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderConfirmationEventListner  {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailSendarUtil emailSendarUtil;

    @EventListener
    @Async
    public void onApplicationEvent(OrderConfirmationDetailsEvent event) {
        OrderConfirmationDetails orderConfirmationDetails = (OrderConfirmationDetails) event.getSource();
        User user = orderConfirmationDetails.getUser();
        String email = user.getEmail();
        String userName = orderConfirmationDetails.getName();
        String restaurantName = "HappyMeals";
        String emailBody = "Subject: Order Confirmation - " + orderConfirmationDetails.getOrderNumber() + "\n\n" +
                "Dear " + userName + ",\n\n" +
                "Thank you for your order! We are pleased to confirm that your payment has been received and your order is being processed. Here are the details of your order:\n\n" +
                "Order Number: " + orderConfirmationDetails.getOrderNumber() + "\n" +
                "Order Date: " + orderConfirmationDetails.getOrderDate() + "\n" +
                "\nOrder Summary:\n" + orderConfirmationDetails.getMenuItems() + "\n" +
                "Total Amount Paid: $" + orderConfirmationDetails.getTotalAmountPaid() + "\n\n" +
                "Delivery Details:\nEstimated Delivery Time: " + orderConfirmationDetails.getDeliveryTimeEstimate() + "\n" +
                "Delivery Address: " + orderConfirmationDetails.getDefaultDeliveryAddress() + "\n\n" +
                "Payment Method: " + orderConfirmationDetails.getPaymentMethod() + "\n" +
                "Contact Phone: " + orderConfirmationDetails.getContactPhone() + "\n" +
                "Contact Email: " + orderConfirmationDetails.getContactEmail() + "\n\n" +
                "You can track the status of your order using the following link: " + orderConfirmationDetails.getTrackingUrl() + "\n\n" +
                "If you have any questions or need assistance, feel free to contact us at " + restaurantName + " or " + orderConfirmationDetails.getContactEmail() + ".\n\n" +
                "Thank you for choosing " + restaurantName + "! We hope you enjoy your meal.\n\n" +
                "Best regards,\n" + restaurantName;
        emailSendarUtil.sendEmailWithMultipleBodyLine(email , List.of(emailBody) , "Order Confirmation  " + " " + orderConfirmationDetails.getOrderNumber());
        log.info( " email sent  SuccessFully !");
    }
}
