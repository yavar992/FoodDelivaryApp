package com.foodDelivaryApp.userservice.listner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.event.WalletEvent;
import com.foodDelivaryApp.userservice.util.IEmailSender;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WalletEventListener implements ApplicationListener<WalletEvent> {

    @Autowired
    private IEmailSender emailSender;

    @EventListener
    @Override
    public void onApplicationEvent(WalletEvent event) {
        User user = (User) event.getSource();
        String email = user.getEmail();
        System.out.println("email--> " + email);
        String amount  = String.valueOf(50);

        emailSender.sendEmail(email , "walletCredit.txt", "Wallet Credited");


    }
}
