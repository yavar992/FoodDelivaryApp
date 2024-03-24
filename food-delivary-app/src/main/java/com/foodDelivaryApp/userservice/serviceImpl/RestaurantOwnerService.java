package com.foodDelivaryApp.userservice.serviceImpl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodDelivaryApp.userservice.convertor.RestaurantOwnerConvertor;
import com.foodDelivaryApp.userservice.dto.ChangePasswordDTO;
import com.foodDelivaryApp.userservice.dto.RestaurantOwnerDTO;
import com.foodDelivaryApp.userservice.dto.VerifyOTP;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.entity.Roles;
import com.foodDelivaryApp.userservice.event.RestaurantOwnerEvent;
import com.foodDelivaryApp.userservice.exceptionHandling.InvalidOTPException;
import com.foodDelivaryApp.userservice.exceptionHandling.OTPExpireException;
import com.foodDelivaryApp.userservice.exceptionHandling.UnverifiedUserException;
import com.foodDelivaryApp.userservice.exceptionHandling.UserNotFoundException;
import com.foodDelivaryApp.userservice.repository.RestaurantsOwnerRepository;
import com.foodDelivaryApp.userservice.repository.RolesRepository;
import com.foodDelivaryApp.userservice.service.IRestaurantOwnerService;
import com.foodDelivaryApp.userservice.util.IEmailSender;
import com.foodDelivaryApp.userservice.util.OTPUtil;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class RestaurantOwnerService implements IRestaurantOwnerService {

    @Autowired
    private RestaurantsOwnerRepository restaurantsOwnerRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private IEmailSender emailSender;

    @Autowired
    private RolesRepository rolesRepository;

    @PostConstruct
    public void hi() {
        Roles roles1 = rolesRepository.findByName("ROLE_RESTAURANTS_OWNER").get();
        log.info("roles {}", roles1);
    }

    @Override
    public String saveRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO) {
        RestaurantOwner restaurantOwner = RestaurantOwnerConvertor
                .convertRestaurantOwnerDTOToRestaurantOwner(restaurantOwnerDTO);
        Set<Roles> roles = new HashSet<>();
        Roles roles1 = rolesRepository.findByName("ROLE_RESTAURANTS_OWNER").get();
        roles.add(roles1);
        restaurantOwner.setRoles(roles);
        RestaurantOwnerEvent restaurantOwnerEvent = new RestaurantOwnerEvent(restaurantOwner);
        applicationEventPublisher.publishEvent(restaurantOwnerEvent);
        restaurantsOwnerRepository.save(restaurantOwner);
        return "Restaurant Owner SingUp Successfully !";
    }

    @Override
    public boolean ownerExistByEmail(String email) {
        Optional<RestaurantOwner> restaurantOwner = restaurantsOwnerRepository.findByEmail(email);
        return restaurantOwner.isPresent();
    }

    @Override
    public String verifyRestaurantOwner(VerifyOTP verifyOTP) {
        if (verifyOTP.getOtp() == null) {
            throw new InvalidOTPException("Please enter the otp ");
        }
        RestaurantOwner restaurantOwner = restaurantsOwnerRepository.findByOtp(verifyOTP.getOtp());
        if (restaurantOwner == null) {
            throw new InvalidOTPException("Incorrect OTP , please Enter the correct OTP ");
        }
        if (restaurantOwner.getOtp() == null) {
            throw new InvalidOTPException("OTP has expired , Please request for the new OTP");
        }
        if (!restaurantOwner.getOtp().equals(verifyOTP.getOtp())) {
            throw new InvalidOTPException("Incorrect OTP , Please enter the correct OTP");
        }
        if (restaurantOwner.getOtpExpireTime().isBefore(LocalDateTime.now())) {
            throw new OTPExpireException("OTP has expired , Please request a new OTP");
        }
        restaurantOwner.setIsVerified(true);
        restaurantOwner.setOtp(null);
        restaurantOwner.setOtpSendingTime(null);
        restaurantOwner.setOtpExpireTime(null);
        restaurantsOwnerRepository.saveAndFlush(restaurantOwner);
        return "COOL , Your account has been successfully verified";
    }

    @Override
    public String resendOTP(String email) {
        RestaurantOwner restaurantOwner = findByEmail(email);
        int otp = OTPUtil.generateOTP();
        emailSender.sendEmail(restaurantOwner.getEmail(), "otp.txt", "OTP VERIFICATION");
        restaurantOwner.setOtp(otp);
        restaurantOwner.setOtpSendingTime(LocalDateTime.now());
        restaurantOwner.setOtpExpireTime(LocalDateTime.now().plusMinutes(15));
        restaurantsOwnerRepository.saveAndFlush(restaurantOwner);
        return "OTP resend successfully !";
    }

    @Override
    public String forgetPassword(String email) {
        RestaurantOwner restaurantOwner = findByEmail(email);
        int OTP = OTPUtil.generateOTP();
        restaurantOwner.setOtp(OTP);
        emailSender.sendEmail(restaurantOwner.getEmail(),
                "forgotPassword.txt",
                "One-Time Password (OTP) for verifying your password");
        restaurantsOwnerRepository.saveAndFlush(restaurantOwner);
        return "please enter the otp sent to your email and the new password you want to change";
    }

    @Override
    public String changePassword(ChangePasswordDTO changePasswordDTO) {
        if (changePasswordDTO.getOtp() == null || changePasswordDTO.getPassword() == null) {
            throw new InvalidOTPException("OTP or Password cannot be null , please enter both the field");
        }

        RestaurantOwner restaurantOwner = restaurantsOwnerRepository.findByOtp(changePasswordDTO.getOtp());
        if (restaurantOwner == null) {
            throw new InvalidOTPException("Incorrect OTP");
        }

        Integer otp = restaurantOwner.getOtp();
        if (!otp.equals(changePasswordDTO.getOtp())) {
            throw new InvalidOTPException("Incorrect OTP");
        }

        restaurantOwner.setPassword(changePasswordDTO.getPassword());
        restaurantOwner.setOtp(null);
        restaurantsOwnerRepository.saveAndFlush(restaurantOwner);
        return "Password change successfully !";
    }

    @Override
    public RestaurantOwner findById(Long id) {
        RestaurantOwner restaurantOwner = restaurantsOwnerRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No restaurant owner found for the id " + id));
        if (!restaurantOwner.getIsVerified()) {
            throw new UnverifiedUserException("User is not verified please verify your account first");
        }
        return restaurantOwner;
    }

    @Override
    public RestaurantOwner findByEmail(String email) {
        Optional<RestaurantOwner> owner = restaurantsOwnerRepository.findByEmail(email);
        if (owner.isEmpty()) {
            throw new UserNotFoundException(String.format("No restaurant owner found for email %s ", email));
        }

        return owner.get();
    }

}
