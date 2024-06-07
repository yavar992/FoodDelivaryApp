package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.DTO.ChangePasswordDTO;
import com.foodDelivaryApp.userservice.DTO.DeliveryGuyProfileDTO;
import com.foodDelivaryApp.userservice.DTO.ShiftDTO;
import com.foodDelivaryApp.userservice.DTO.VerifyOTP;
import com.foodDelivaryApp.userservice.Enums.DeliveryGuyStatusEnum;
import com.foodDelivaryApp.userservice.Enums.TransactionEnum;
import com.foodDelivaryApp.userservice.Enums.WalletMethodEnum;
import com.foodDelivaryApp.userservice.convertor.DeliveryGuyMapper;
import com.foodDelivaryApp.userservice.entity.*;
import com.foodDelivaryApp.userservice.event.DeliveryGuyEvent;
import com.foodDelivaryApp.userservice.event.UserRegisterationEvent;
import com.foodDelivaryApp.userservice.event.WalletEvent;
import com.foodDelivaryApp.userservice.exceptionHandling.*;
import com.foodDelivaryApp.userservice.repository.DeliveryGuyRepo;
import com.foodDelivaryApp.userservice.repository.RestaurantRepo;
import com.foodDelivaryApp.userservice.repository.RolesRepository;
import com.foodDelivaryApp.userservice.service.DeliveryGuyService;
import com.foodDelivaryApp.userservice.util.CommonUtil;
import com.foodDelivaryApp.userservice.util.EmailSendarUtil;
import com.foodDelivaryApp.userservice.util.ImageUtil;
import com.foodDelivaryApp.userservice.util.OTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeliveryGuyServiceImpl implements DeliveryGuyService {

    @Autowired
    private DeliveryGuyRepo deliveryGuyRepo;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private EmailSendarUtil emailSendarUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RestaurantRepo restaurantRepo;


    @Override
    public boolean userAlreadyExistByEmailOrUserName(String email, String username) {
        DeliveryGuy deliveryGuy = deliveryGuyRepo.findByEmailOrUserName(email, username);
        return deliveryGuy != null;
    }

    @Override
    public String saveDeliveryGuy(DeliveryGuy deliveryGuy, String referralCode) {
        if (referralCode!=null){
            DeliveryGuy deliveryGuy1 = deliveryGuyRepo.findUserByReferralCode(referralCode);
            if (deliveryGuy1==null){
                throw new UserNotFoundException("Invalid referral code , Please enter the correct referral code  " );
            }
        }
        Set<Roles> rolesSet = new HashSet<>();
        Optional<Roles> roles = rolesRepository.findByName("ROLE_DELIVERY_BOY");
        Roles roles1;
        if (roles.isPresent()){
            roles1 = roles.get();
            rolesSet.add(roles1);
        }
        else{
            throw new UserNotFoundException("Invalid Role !!!");
        }

        log.info("Roles {}" , roles1);
        rolesSet.add(roles1);
        deliveryGuy.setRoles(rolesSet);
        if (referralCode!=null){
            Set<DeliveryGuy> deliveryGuys = new HashSet<>();
            DeliveryGuy deliveryGuy1 = deliveryGuyRepo.findDeliveryGuyWithReferralCode(referralCode);
            deliveryGuys.add(deliveryGuy1);
            deliveryGuy.setReferredDeliveryGuy(deliveryGuys);
        }
        deliveryGuyRepo.saveAndFlush(deliveryGuy);
        DeliveryGuyEvent deliveryGuyEvent = new DeliveryGuyEvent(deliveryGuy);
        applicationEventPublisher.publishEvent(deliveryGuyEvent);


        return "DeliveryGuy Registered SuccessFully !";
    }

    @Override
    public Object verifyUserAccount(VerifyOTP verifyOTP) {
        if (verifyOTP.getOtp()==null){
            throw new InvalidOTPException("Please enter the otp ");
        }
            DeliveryGuy deliveryGuy = deliveryGuyRepo.findByOTP(verifyOTP.getOtp());
        if(deliveryGuy==null){
            throw new UserNotFoundException("Incorrect OTP , please Enter the correct OTP ");
        }
        if(deliveryGuy.getOtp()==null){
            throw new InvalidOTPException("OTP has expired , Please request for the new OTP");
        }
        if(!deliveryGuy.getOtp().equals(verifyOTP.getOtp())){
            throw new InvalidOTPException("Incorrect OTP , Please enter the correct OTP");
        }
        if(deliveryGuy.getOtpExpireTime().isBefore(LocalDateTime.now())){
            deliveryGuy.setOtpSendingTime(null);
            deliveryGuy.setOtpExpireTime(null);
            deliveryGuy.setOtp(null);
            throw new OTPExpireException("OTP has expired , Please request a new OTP");
        }
        deliveryGuy.setVerified(true);
        deliveryGuy.setOtp(null);
        deliveryGuy.setOtpSendingTime(null);
        deliveryGuy.setOtpExpireTime(null);

        deliveryGuyRepo.saveAndFlush(deliveryGuy);

        return "COOL , Your account has been successfully verified";
    }

    @Override
    public Object resendOTP(String email) {
        return null;
    }


    public String resendOTPToDeliveryGuy(DeliveryGuy deliveryGuy) {
        String userEmail = deliveryGuy.getEmail();
        int otp = OTPUtil.otp();
        log.info("OTP {}", otp);
        emailSendarUtil.sendEmailWithMultipleBodyLine(userEmail, Arrays.asList("Dear " + deliveryGuy.getFirstName() + " " + deliveryGuy.getLastName() + ",",
                "",
                "Thank you for signing up for HappyMeal!",
                "To complete your registration, please enter the following one-time password (OTP): " + otp,
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
        deliveryGuy.setOtpSendingTime(LocalDateTime.now());
        deliveryGuy.setOtpExpireTime(LocalDateTime.now().plusMinutes(15));
        deliveryGuy.setOtp(otp);
        deliveryGuyRepo.saveAndFlush(deliveryGuy);
        return "OTP send successfully !";
    }

    @Override
    public String forgetPassword(String email) {
        DeliveryGuy deliveryGuy = deliveryGuyRepo.findByEmail(email);
        if (deliveryGuy==null){
            throw new UserNotFoundException("User not found for the email " + email);
        }
        if (!deliveryGuy.isVerified()){
            throw new UnverifiedUserException("user is not verified , please verify yourself first ");
        }
        int OTP = OTPUtil.otp();
        log.info("OTP {}" , OTP);
        deliveryGuy.setOtp(OTP);
        emailSendarUtil.sendEmailWithMultipleBodyLine(deliveryGuy.getEmail() ,  Arrays.asList("Dear " + deliveryGuy.getFirstName() + " " + deliveryGuy.getLastName() +
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
                "The HappyMeal Team") , "One-Time Password (OTP) for verifying your password");
        deliveryGuyRepo.saveAndFlush(deliveryGuy);
        return "please enter the otp sent to your email and the new password you want to change";
    }

    @Override
    public String changePassword(ChangePasswordDTO changePasswordDTO) {
        if (changePasswordDTO.getOtp()==null || changePasswordDTO.getPassword()==null) {
            throw new InvalidOTPException("OTP or Password cannot be null , please enter both the field");
        }
        DeliveryGuy deliveryGuy = deliveryGuyRepo.findByOTP(changePasswordDTO.getOtp());
        if (deliveryGuy==null){
            throw new InvalidOTPException("Incorrect OTP");
        }
        Integer otp = deliveryGuy.getOtp();
        if (!otp.equals(changePasswordDTO.getOtp())){
            throw new InvalidOTPException("Incorrect OTP");
        }
        deliveryGuy.setPassword(changePasswordDTO.getPassword());
        deliveryGuy.setOtp(null);
        deliveryGuyRepo.saveAndFlush(deliveryGuy);
        return "Password change successfully !";
    }

    @Override
    public String uploadImages(Authentication authentication, MultipartFile file) throws IOException {
       DeliveryGuy deliveryGuy =  commonUtil.authenticateDeliveryGuy(authentication);
        if(file.getSize()/1024>=15){
            throw new LargeImageSizeException("Image size should not be more than 10 kb");
        }
        deliveryGuy.setFileName(file.getName());
        deliveryGuy.setFileType(file.getContentType());
        deliveryGuy.setProfilePicture(ImageUtil.compressImage(file.getBytes()));
        deliveryGuyRepo.save(deliveryGuy);
        return "images upload Successfully !";
    }

    @Override
    public String updateImage(Authentication authentication, MultipartFile file) throws IOException {
        DeliveryGuy deliveryGuy = commonUtil.authenticateDeliveryGuy(authentication);
        deliveryGuy.setFileType(file.getContentType());
        deliveryGuy.setFileName(file.getName());
        deliveryGuy.setProfilePicture(ImageUtil.compressImage(file.getBytes()));
        deliveryGuyRepo.save(deliveryGuy);
        return "Image Updated successfully !";
    }

    @Override
    public String chooseShiftTiminfOfDeliveyBoy(Authentication authentication , ShiftDTO shiftDTO) {

        DeliveryGuy deliveryGuy =  commonUtil.authenticateDeliveryGuy(authentication);
        deliveryGuy.setShiftStart(shiftDTO.getShiftStart().toUpperCase());
        deliveryGuy.setShiftEnd(shiftDTO.getShiftEnd().toUpperCase());
        deliveryGuyRepo.saveAndFlush(deliveryGuy);

        return "DONE ";
    }

    @Override
    public String setPreferredDeliveryZones(Authentication authentication, List<String> preferredDeliveryZones) {
        DeliveryGuy deliveryGuy = commonUtil.authenticateDeliveryGuy(authentication);
        deliveryGuy.setPreferredDeliverZones(String.join(",", preferredDeliveryZones));
         deliveryGuyRepo.save(deliveryGuy);
         return "Delivery Zones selected Successfully";
    }

    @Override
    public String assignRestaurantToDeliveryGuy(Authentication authentication, Long restaurantId) {
        DeliveryGuy deliveryGuy = commonUtil.authenticateDeliveryGuy(authentication);
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(()-> new RestaurantMenuException(" No restaurant found for id " + restaurantId));

        deliveryGuy.getRestaurants().add(restaurant);
         deliveryGuyRepo.save(deliveryGuy);
         return "Registration with Restaurant successful !";
    }

    @Override
    public DeliveryGuyProfileDTO getProfile(Authentication authentication) {
        DeliveryGuy deliveryGuy = commonUtil.authenticateDeliveryGuy(authentication);
        return DeliveryGuyMapper.INSTANCE.deliveryGuyToDeliveryGuyProfileDTO(deliveryGuy);
    }

    @Override
    public DeliveryGuyProfileDTO updateDeliveryGuy(Authentication authentication, DeliveryGuyProfileDTO updateDTO) {
        DeliveryGuy deliveryGuy = commonUtil.authenticateDeliveryGuy(authentication);
        updateDeliveryGuyFromDTO(updateDTO, deliveryGuy);
        deliveryGuyRepo.save(deliveryGuy);
        return DeliveryGuyMapper.INSTANCE.deliveryGuyToDeliveryGuyProfileDTO(deliveryGuy);

    }

    @Override
    public String deleteUser(Authentication authentication) {
        DeliveryGuy deliveryGuy = commonUtil.authenticateDeliveryGuy(authentication);
        deliveryGuyRepo.delete(deliveryGuy);
        return "DeliverGuy deleted successfully !!";
    }

    @Override
    public List<DeliveryGuyProfileDTO> listAllDeliveryGuys() {
        List<DeliveryGuy> deliveryGuys = deliveryGuyRepo.findAll();
        if (deliveryGuys.isEmpty()){
            throw new DeliveryGuyException("No deliveryGuy found in the database !!");
        }
        return deliveryGuys.stream()
                .map(DeliveryGuyMapper.INSTANCE::deliveryGuyToDeliveryGuyProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryGuyProfileDTO searchDeliveryGuy(String username, String email) {
        DeliveryGuy deliveryGuy = deliveryGuyRepo.findByUsernameOrEmail(username, email);
        if (deliveryGuy==null){
            throw new DeliveryGuyException("No deliveryBoy found for username " + username);
        }
            return DeliveryGuyMapper.INSTANCE.deliveryGuyToDeliveryGuyProfileDTO(deliveryGuy);
    }

    @Override
    public String updateStatusOfDeliveryGuy(Authentication authentication, DeliveryGuyStatusEnum statusUpdate) {
        DeliveryGuy deliveryGuy = commonUtil.authenticateDeliveryGuy(authentication);
        deliveryGuy.setStatus(statusUpdate);
        deliveryGuyRepo.saveAndFlush(deliveryGuy);
        return "Status updated successfully !!";
    }


    @Override
    public DeliveryGuy findByDeliveryEmail(String email) {
        DeliveryGuy deliveryGuy = deliveryGuyRepo.findByEmail(email);
        if (deliveryGuy==null){
            throw new DeliveryGuyException("No deliveryBoy found for " + email);
        }
        return deliveryGuy;
    }


    public void updateDeliveryGuyFromDTO(DeliveryGuyProfileDTO deliveryGuyProfileDTO, DeliveryGuy deliveryGuy) {
        deliveryGuy.setUsername(deliveryGuyProfileDTO.getUsername());
        deliveryGuy.setEmail(deliveryGuyProfileDTO.getEmail());
        deliveryGuy.setDateOfBirth(deliveryGuyProfileDTO.getDateOfBirth());
        deliveryGuy.setPhoneNumber(deliveryGuyProfileDTO.getPhoneNumber());
        deliveryGuy.setAddress(deliveryGuyProfileDTO.getAddress());
        deliveryGuy.setCity(deliveryGuyProfileDTO.getCity());
        deliveryGuy.setState(deliveryGuyProfileDTO.getState());
        deliveryGuy.setCountry(deliveryGuyProfileDTO.getCountry());
        deliveryGuy.setShiftStart(deliveryGuyProfileDTO.getShiftStart());
        deliveryGuy.setShiftEnd(deliveryGuyProfileDTO.getShiftEnd());
        deliveryGuy.setPreferredDeliverZones(deliveryGuyProfileDTO.getPreferredDeliverZones());
    }






}
