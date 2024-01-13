package com.foodDelivaryApp.userservice.listner;

import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.event.WalletEvent;
import com.foodDelivaryApp.userservice.util.EmailSendarUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class WalletEventListener implements ApplicationListener<WalletEvent> {

    @Autowired
    private EmailSendarUtil emailSendarUtil;

    @EventListener
    @Override
    public void onApplicationEvent(WalletEvent event) {
        User user = (User) event.getSource();
        String email = user.getEmail();
        System.out.println("email--> " + email);
        String amount  = String.valueOf(50);

        emailSendarUtil.sendEmailWithMultipleBodyLine(email , Arrays.asList(
                "Dear " + user.getUsername() + ",\n\n" +
                        "We are excited to inform you that your HappyMeals wallet has been credited with " + amount + " rupees!\n\n" +
                        "You can now enjoy seamless transactions and explore the exciting features of HappyMeals.\n\n" +
                        "If you have any questions or need assistance, feel free to contact our support team at support@happymeals.com.\n\n" +
                        "Thank you for choosing HappyMeals!\n\n" +
                        "Best Regards,\n" +
                        "The HappyMeals Team"
        ), "Wallet Credited");


    }
}
