package com.foodDelivaryApp.userservice.listner;

import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.event.RestaurantOwnerEvent;
import com.foodDelivaryApp.userservice.repository.RestaurantsOwnerRepo;
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
public class RestaurantOwnerEventListner  {

    //implements ApplicationListener<RestaurantOwnerEvent>

    @Autowired
    private RestaurantsOwnerRepo restaurantsOwnerRepo;

    @Autowired
    private EmailSendarUtil emailSendarUtil;


    @Async
    @EventListener
    public void onApplicationEvent(RestaurantOwnerEvent event) {
       RestaurantOwner restaurantOwner = (RestaurantOwner) event.getSource();
       String email = restaurantOwner.getEmail();
       Integer otp = OTPUtil.otp();
       restaurantOwner.setOtp(otp);
       restaurantOwner.setOtpSendingTime(LocalDateTime.now());
       LocalDateTime otpExpireDate= LocalDateTime.now().plusMinutes(15);
       restaurantOwner.setOtpExpireTime(otpExpireDate);

       emailSendarUtil.sendEmailWithMultipleBodyLine(email , Arrays.asList("Dear " + restaurantOwner.getFirstName() + " " + restaurantOwner.getLastName() + " \n\n"
               +  "Welcome to HappyMeal food delivery services!\n\n"
               + "To complete the registration process, please enter the following OTP (One-Time Password) within the next 15 minutes:\n\n"
               + "OTP: " + otp
               + "Please note that this OTP will expire at " + otpExpireDate + ".\n\n"
               + "If you have any questions or need assistance, please feel free to contact our support team.\n\n"
               + "Best regards,\n"
               + "The Happy Meal  Team"
               ) , "OTP VERIFICATION");
        restaurantsOwnerRepo.save(restaurantOwner);
        log.info(restaurantOwner.getFirstName() + " updated SuccessFully !");
    }
}
