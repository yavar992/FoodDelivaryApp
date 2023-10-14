package com.foodDelivaryApp.userservice.listner;

import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.event.UserRegisterationEvent;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.util.EmailSendarUtil;
import com.foodDelivaryApp.userservice.util.OTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserRegisterationEventListner  {

//    implements ApplicationListener<UserRegisterationEvent>

    @Autowired
    private EmailSendarUtil emailSendarUtil;

    @Autowired
    private UserRepo userRepo;


    @Async
    @EventListener
    public void onApplicationEvent(UserRegisterationEvent event) {
        User user = (User) event.getSource();
        String email = user.getEmail();
        String userName = user.getFirstName()+ " " + user.getLastName();
        Integer otpSendToUser = OTPUtil.otp();
        user.setOtp(otpSendToUser);
        user.setOtpSendingTime(LocalDateTime.now());
        user.setOtpExpireTime(LocalDateTime.now().plusMinutes(15));
        userRepo.saveAndFlush(user);

        emailSendarUtil.sendEmailWithMultipleBodyLine(email , Arrays.asList("Dear " + userName + ",\n\n" +
                "Thank you for signing up for HappyMeal! To complete your registration, please enter the following one-time password (OTP): " + otpSendToUser + ",\n\n" +
                "This OTP is valid for 15 minutes from the time of this email. If you do not verify your email within this timeframe, you will need to request a new OTP.\n\n" +
                "Once you have verified your email address, you will be able to log in to your HappyMeal account and start using our services.\n\n" +
                "If you have any questions, please don't hesitate to contact us at abcd@gmail.com .\n\n" +
                "We look forward to seeing you soon!\n\n" +
                "Sincerely \n" +
                "The HappyMeal Team \n"

        ), "OTP VERIFICATION");
        log.info("mail sent successfully ");
    }
}

