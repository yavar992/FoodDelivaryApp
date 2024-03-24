package com.foodDelivaryApp.userservice.listner;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.event.RestaurantOwnerEvent;
import com.foodDelivaryApp.userservice.repository.RestaurantsOwnerRepository;
import com.foodDelivaryApp.userservice.util.IEmailSender;
import com.foodDelivaryApp.userservice.util.OTPUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RestaurantOwnerEventListner  {

    //implements ApplicationListener<RestaurantOwnerEvent>

    @Autowired
    private RestaurantsOwnerRepository restaurantsOwnerRepo;

    @Autowired
    private IEmailSender emailSendarUtil;


    @Async
    @EventListener
    public void onApplicationEvent(RestaurantOwnerEvent event) {
       RestaurantOwner restaurantOwner = (RestaurantOwner) event.getSource();
       String email = restaurantOwner.getEmail();
       Integer otp = OTPUtil.generateOTP();
       restaurantOwner.setOtp(otp);
       restaurantOwner.setOtpSendingTime(LocalDateTime.now());
       LocalDateTime otpExpireDate= LocalDateTime.now().plusMinutes(15);
       restaurantOwner.setOtpExpireTime(otpExpireDate);

       emailSendarUtil.sendEmail(email , "otp.txt" , "OTP VERIFICATION");
        restaurantsOwnerRepo.save(restaurantOwner);
        log.info(restaurantOwner.getFirstName() + " updated SuccessFully !");
    }
}
