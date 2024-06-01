package com.foodDelivaryApp.userservice.listner;

import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import com.foodDelivaryApp.userservice.entity.DeliveryGuyRating;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.event.OrderConfirmationDetailsEvent;
import com.foodDelivaryApp.userservice.repository.DeliveryGuyRepo;
import com.foodDelivaryApp.userservice.util.EmailSendarUtil;
import com.foodDelivaryApp.userservice.util.OTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Slf4j
public class DeliveryGuyEventListner {

    @Autowired
    private DeliveryGuyRepo deliveryGuyRepo;

    @Autowired
    EmailSendarUtil emailSendarUtil;

    @EventListener
    @Async
    public void onApplicationEvent(OrderConfirmationDetailsEvent event) {
        DeliveryGuy deliveryGuy = (DeliveryGuy) event.getSource();
        String email = deliveryGuy.getEmail();
        String userName = deliveryGuy.getFirstName() + " " + deliveryGuy.getLastName();
        int otpSendToUser = OTPUtil.otp();
        log.info("OTP {}", otpSendToUser);
        deliveryGuy.setOtp(otpSendToUser);
        deliveryGuy.setOtpSendingTime(LocalDateTime.now());
        deliveryGuy.setOtpExpireTime(LocalDateTime.now().plusMinutes(15));
        deliveryGuyRepo.saveAndFlush(deliveryGuy);
        emailSendarUtil.sendEmailWithMultipleBodyLine(
                email,
                Arrays.asList(
                        "Dear " + userName + ",\n\n" +
                                "Welcome to HappyMeal! We are excited to have you as a part of our delivery team. To complete your registration and start delivering, please enter the following one-time password (OTP): " + otpSendToUser + ".\n\n" +
                                "This OTP is valid for 15 minutes from the time of this email. If you do not verify your email within this timeframe, you will need to request a new OTP.\n\n" +
                                "Once you have verified your email address, you will be able to log in to your HappyMeal account, accept delivery assignments, and start earning.\n\n" +
                                "Here are some steps to get you started:\n" +
                                "1. Download our delivery app from the App Store or Google Play.\n" +
                                "2. Log in with your credentials and the OTP provided.\n" +
                                "3. Complete your profile and set your availability.\n" +
                                "4. Start accepting delivery requests and earning money.\n\n" +
                                "If you have any questions or need assistance, please don't hesitate to contact us at support@happymeal.com.\n\n" +
                                "We look forward to working with you and making our customers happy together!\n\n" +
                                "Sincerely,\n" +
                                "The HappyMeal Team\n"
                ),
                "Welcome to HappyMeal - OTP Verification"
        );

        log.info("mail sent successfully ");

    }
}


