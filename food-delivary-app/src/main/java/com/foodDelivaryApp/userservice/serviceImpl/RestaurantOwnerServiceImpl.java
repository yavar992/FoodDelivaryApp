package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.DTO.ChangePasswordDTO;
import com.foodDelivaryApp.userservice.DTO.RestaurantOwnerDTO;
import com.foodDelivaryApp.userservice.DTO.VerifyOTP;
import com.foodDelivaryApp.userservice.convertor.RestaurantOwnerConvertor;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.entity.Roles;
import com.foodDelivaryApp.userservice.event.RestaurantOwnerEvent;
import com.foodDelivaryApp.userservice.exceptionHandling.InvalidOTPException;
import com.foodDelivaryApp.userservice.exceptionHandling.OTPExpireException;
import com.foodDelivaryApp.userservice.exceptionHandling.UnverifiedUserException;
import com.foodDelivaryApp.userservice.exceptionHandling.UserNotFoundException;
import com.foodDelivaryApp.userservice.repository.RestaurantsOwnerRepo;
import com.foodDelivaryApp.userservice.repository.RolesRepository;
import com.foodDelivaryApp.userservice.service.RestaurantOwnerService;
import com.foodDelivaryApp.userservice.util.EmailSendarUtil;
import com.foodDelivaryApp.userservice.util.OTPUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@Transactional
public class RestaurantOwnerServiceImpl implements RestaurantOwnerService {

        @Autowired
        private RestaurantsOwnerRepo restaurantsOwnerRepo;

        @Autowired
        private ApplicationEventPublisher applicationEventPublisher;

        @Autowired
        EmailSendarUtil emailSendarUtil;

        @Autowired
        RolesRepository rolesRepository;

//        @PostConstruct
//        public void hi(){
//            Roles roles1 = rolesRepository.findByName("ROLE_RESTAURANTS_OWNER").get();
//            log.info("roles {}",roles1);
//        }



        @Override
        public String saveRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO) {
           RestaurantOwner restaurantOwner =
                   RestaurantOwnerConvertor.convertRestaurantOwnerDTOToRestaurantOwner(restaurantOwnerDTO);
            Set<Roles> roles = new HashSet<>();
            Roles roles1 = rolesRepository.findByName("ROLE_RESTAURANTS_OWNER").get();
            roles.add(roles1);
            restaurantOwner.setRoles(roles);
            RestaurantOwnerEvent restaurantOwnerEvent = new RestaurantOwnerEvent(restaurantOwner);
            applicationEventPublisher.publishEvent(restaurantOwnerEvent);
           restaurantsOwnerRepo.save(restaurantOwner);
            return  "Restaurant Owner SingUp Successfully !";
        }

        @Override
        public boolean ownerExistByEmail(String email) {
            Optional<RestaurantOwner> restaurantOwner = restaurantsOwnerRepo.findByEmail(email);
            RestaurantOwner restaurantOwner1 = restaurantOwner.get();
            return restaurantOwner!=null;
        }

        @Override
        public String verifyRestaurantOwner(VerifyOTP verifyOTP) {
            if (verifyOTP.getOtp()==null){
                throw new InvalidOTPException("Please enter the otp ");
            }
            RestaurantOwner restaurantOwner = restaurantsOwnerRepo.findByOtp(verifyOTP.getOtp());
            if (restaurantOwner==null){
                throw new InvalidOTPException("Incorrect OTP , please Enter the correct OTP ");
            }
            if(restaurantOwner.getOtp()==null){
                throw new InvalidOTPException("OTP has expired , Please request for the new OTP");
            }
            if(!restaurantOwner.getOtp().equals(verifyOTP.getOtp())){
                throw new InvalidOTPException("Incorrect OTP , Please enter the correct OTP");
            }
            if(restaurantOwner.getOtpExpireTime().isBefore(LocalDateTime.now())){
                throw new OTPExpireException("OTP has expired , Please request a new OTP");
            }
            restaurantOwner.setIsVerified(true);
            restaurantOwner.setOtp(null);
            restaurantOwner.setOtpSendingTime(null);
            restaurantOwner.setOtpExpireTime(null);
            restaurantsOwnerRepo.saveAndFlush(restaurantOwner);
            return "COOL , Your account has been successfully verified";
        }

    @Override
    public String resendOTP(String email) {
        Optional<RestaurantOwner> restaurantOwner = restaurantsOwnerRepo.findByEmail(email);
        if (restaurantOwner.isEmpty()){
            throw new UserNotFoundException("User not Found for the email " + email + " please enter the correct email");
        }
        RestaurantOwner restaurantOwner1 = restaurantOwner.get();
        int OTP = OTPUtil.otp();
        emailSendarUtil.sendEmailWithMultipleBodyLine(restaurantOwner1.getEmail(),
                Arrays.asList("Dear " + restaurantOwner1.getFirstName() + " " + restaurantOwner1.getLastName() +
                "Thank you for signing up for HappyMeal!",
                "To complete your registration, please enter the following one-time password (OTP): " + OTP,
                "",
                "This OTP is valid for 15 minutes from the time of this email. If you do not verify your email within this timeframe, you will need to request a new OTP.",
                "",
                "Once you have verified your email address, you will be able to log in to your HappyMeal account and start using our services.",
                "",
                "If you have any questions, please don't hesitate to contact us at abcd@gmail.com.",
                "",
                "We look forward to seeing you soon!",
                "",
                "Sincerely,",
                "The HappyMeal Team"
        ), "OTP VERIFICATION");
        restaurantOwner1.setOtp(OTP);
        restaurantOwner1.setOtpSendingTime(LocalDateTime.now());
        restaurantOwner1.setOtpExpireTime(LocalDateTime.now().plusMinutes(15));
        restaurantsOwnerRepo.saveAndFlush(restaurantOwner1);
        return "OTP resend successfully !";
    }

    @Override
    public String forgetPassword(String email) {
        Optional<RestaurantOwner> restaurantOwner = restaurantsOwnerRepo.findByEmail(email);
        if (restaurantOwner.isEmpty()){
            throw new UserNotFoundException("User not found for the email " + email);
        }
        RestaurantOwner restaurantOwner1 = restaurantOwner.get();
        if (!restaurantOwner1.getIsVerified()){
            throw new UnverifiedUserException("user is not verified , please verify yourself first ");
        }
        int OTP = OTPUtil.otp();
        restaurantOwner1.setOtp(OTP);
        emailSendarUtil.sendEmailWithMultipleBodyLine(restaurantOwner1.getEmail() , Arrays.asList("Dear " + restaurantOwner1.getFirstName() + " " + restaurantOwner1.getLastName() +
                        "Thank you for signing up for HappyMeal!",
                "To complete your registration, please enter the following one-time password (OTP): " + OTP,
                "",
                "This OTP is valid for 15 minutes from the time of this email. If you do not verify your email within this timeframe, you will need to request a new OTP.",
                "",
                "Once you have verified your email address, you will be able to log in to your HappyMeal account and start using our services.",
                "",
                "If you have any questions, please don't hesitate to contact us at abcd@gmail.com.",
                "",
                "We look forward to seeing you soon!",
                "Sincerely," ,
                "The HappyMeal Team") , "One-Time Password (OTP) for verifying your password");
        restaurantsOwnerRepo.saveAndFlush(restaurantOwner1);
        return "please enter the otp sent to your email and the new password you want to change";
    }

    @Override
    public String changePassword(ChangePasswordDTO changePasswordDTO) {
        if (changePasswordDTO.getOtp()==null || changePasswordDTO.getPassword()==null) {
            throw new InvalidOTPException("OTP or Password cannot be null , please enter both the field");
        }
        RestaurantOwner restaurantOwner = restaurantsOwnerRepo.findByOtp(changePasswordDTO.getOtp());
        if (restaurantOwner==null){
            throw new InvalidOTPException("Incorrect OTP");
        }
        Integer otp = restaurantOwner.getOtp();
        if (!otp.equals(changePasswordDTO.getOtp())){
            throw new InvalidOTPException("Incorrect OTP");
        }
        restaurantOwner.setPassword(changePasswordDTO.getPassword());
        restaurantOwner.setOtp(null);
        restaurantsOwnerRepo.saveAndFlush(restaurantOwner);
        return "Password change successfully !";    
        }

    @Override
    public RestaurantOwner findById(Long id) {
        RestaurantOwner restaurantOwner = restaurantsOwnerRepo.findById(id).orElseThrow(()->new UserNotFoundException("No restaurant owner found for the id " + id));
        if (!restaurantOwner.getIsVerified()){
            throw new UnverifiedUserException("User is not verified please verify your account first");
        }
        return restaurantOwner;
    }


}

