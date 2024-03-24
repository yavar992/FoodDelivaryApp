package com.foodDelivaryApp.userservice.listner;

import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.event.UserRegisterationEvent;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.util.IEmailSender;
import com.foodDelivaryApp.userservice.util.OTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Slf4j
public class UserRegisterationEventListner  {

//    implements ApplicationListener<UserRegisterationEvent>

    @Autowired
    private IEmailSender emailSendarUtil;

    @Autowired
    private UserRepo userRepo;

    @EventListener
    public void onApplicationEvent(UserRegisterationEvent event) {
        User user = (User) event.getSource();
        String email = user.getEmail();
        String userName = user.getFirstName()+ " " + user.getLastName();
        int otpSendToUser = OTPUtil.generateOTP();
        log.info("OTP {}",otpSendToUser);
        user.setOtp(otpSendToUser);
        user.setOtpSendingTime(LocalDateTime.now());
        user.setOtpExpireTime(LocalDateTime.now().plusMinutes(15));
        userRepo.saveAndFlush(user);

        emailSendarUtil.sendEmail(email , "otpVerification.txt", "OTP VERIFICATION");
        log.info("mail sent successfully ");
    }
}

