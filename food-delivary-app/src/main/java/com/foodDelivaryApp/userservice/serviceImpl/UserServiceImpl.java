package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.DTO.*;
import com.foodDelivaryApp.userservice.Enums.TransactionEnum;
import com.foodDelivaryApp.userservice.Enums.WalletMethodEnum;
import com.foodDelivaryApp.userservice.convertor.DeliveryGuyReviewAndRatingMapper;
import com.foodDelivaryApp.userservice.convertor.UserConvertor;
import com.foodDelivaryApp.userservice.entity.*;
import com.foodDelivaryApp.userservice.event.UserRegisterationEvent;
import com.foodDelivaryApp.userservice.event.WalletEvent;
import com.foodDelivaryApp.userservice.exceptionHandling.*;
import com.foodDelivaryApp.userservice.repository.*;
import com.foodDelivaryApp.userservice.service.UserService;
import com.foodDelivaryApp.userservice.util.CommonUtil;
import com.foodDelivaryApp.userservice.util.EmailSendarUtil;
import com.foodDelivaryApp.userservice.util.ImageUtil;
import com.foodDelivaryApp.userservice.util.OTPUtil;
import com.paypal.api.payments.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private  RolesRepository rolesRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private EmailSendarUtil emailSendarUtil;

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private WalletHistoryRepo walletHistoryRepo;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private DeliveryGuyRatingRepo deliveryGuyRatingRepo;

    @Autowired
    private OrderConfirmationRepo orderConfirmationRepo;

    @Override
    public String saveUser(User user , String referralCode) {
        if (referralCode!=null){
            User user2 = userRepo.findUserByReferralCode(referralCode);
            if (user2==null){
                throw new UserNotFoundException("Invalid referral code , Please enter the correct referral code  " );
            }
        }

        Set<Roles> rolesSet = new HashSet<>();
        Optional<Roles> roles = rolesRepository.findByName("ROLE_USER");
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
        user.setRoles(rolesSet);
        UserRegisterationEvent userRegisterationEvent = new UserRegisterationEvent(user);
        applicationEventPublisher.publishEvent(userRegisterationEvent);
        if (referralCode!=null){
            Set<User> users = new HashSet<>();
            User user1 = userRepo.findUserByReferralCode(referralCode);
            users.add(user1);

            user.setReferredUsers(users);
        }
        userRepo.save(user);

        return "User Registered SuccessFully !";
    }

    @Override
    public boolean userAlreadyExistByEmailOrUserName(String email, String username) {
        User user = userRepo.findByEmailOrUserName(email, username);
        return user != null;
    }

    @Override
    public String uploadImages(Long id, MultipartFile file) throws IOException {
        User user = userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("User not found for id : " + id));
        if (!user.isVerified()){
            throw new UnverifiedUserException("Please verified your account first in order to upload the profile image ");
        }

        if(file.getSize()/1024>=15){
            throw new LargeImageSizeException("Image size should not be more than 10 kb");
        }
        user.setFileName(file.getName());
        user.setFileType(file.getContentType());
        user.setProfilePicture(ImageUtil.compressImage(file.getBytes()));
        userRepo.save(user);
        return "images upload Successfully !";
    }

    @Override
    public User findUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("User not found for the id :" + id));
        if (!user.isVerified()){
            throw new UnverifiedUserException("you are not verified , Please verified your account first ");
        }
        return user;
    }

    @Override
    public String updateImage(Long id, MultipartFile file) throws IOException {
        User user = findUserById(id);
        user.setFileType(file.getContentType());
        user.setFileName(file.getName());
        user.setProfilePicture(ImageUtil.compressImage(file.getBytes()));
        userRepo.save(user);
        return "Image Updated successfully !";
    }

    @Override
    public User updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = findUserById(id);
        UserConvertor.updateUser(user , userUpdateDTO);
        return userRepo.saveAndFlush(user);
    }

    @Override
    public String verifyUserAccount(VerifyOTP verifyOTP) {

        if (verifyOTP.getOtp()==null){
            throw new InvalidOTPException("Please enter the otp ");
        }
        User user = userRepo.findByOTP(verifyOTP.getOtp());
        if(user==null){
            throw new UserNotFoundException("Incorrect OTP , please Enter the correct OTP ");
        }
        if(user.getOtp()==null){
            throw new InvalidOTPException("OTP has expired , Please request for the new OTP");
        }
        if(!user.getOtp().equals(verifyOTP.getOtp())){
            throw new InvalidOTPException("Incorrect OTP , Please enter the correct OTP");
        }
        if(user.getOtpExpireTime().isBefore(LocalDateTime.now())){
            user.setOtpSendingTime(null);
            user.setOtpExpireTime(null);
            user.setOtp(null);
            throw new OTPExpireException("OTP has expired , Please request a new OTP");
        }
        user.setVerified(true);
        user.setOtp(null);
        user.setOtpSendingTime(null);
        user.setOtpExpireTime(null);
        Wallet wallet = new Wallet();
        wallet.setBalance(0);
        wallet.setUser(user);
        user.setWallet(wallet);
        userRepo.saveAndFlush(user);
        walletRepo.saveAndFlush(wallet);

        long userId = user.getId();
        Long userWhoReferCode = userRepo.findUserWhoSignUp(userId);
        if (userWhoReferCode!=null){
            User user1 = userRepo.findById(userWhoReferCode).get();
            Wallet wallet1 = user1.getWallet();
            double balance = wallet1.getBalance();
            balance = balance + 50;
            wallet1.setBalance(balance);
            walletRepo.saveAndFlush(wallet1);
            WalletHistory walletHistory = new WalletHistory();
            walletHistory.setUser(user);
            walletHistory.setWallet(wallet);
            walletHistory.setBalance(balance);
            walletHistory.setWalletMethod(WalletMethodEnum.FROM_REFERRAL);
            walletHistory.setTransaction(TransactionEnum.CREDIT);
            walletHistory.setAmount(String.valueOf(50));
            walletHistory.setTransaction_date(LocalDate.now());
            walletHistoryRepo.saveAndFlush(walletHistory);

            WalletEvent walletEvent = new WalletEvent(user1);
            applicationEventPublisher.publishEvent(walletEvent);
        }
        return "COOL , Your account has been successfully verified";
    }

    @Override
    public String resendOTP(String email) {
        User user = userRepo.findByEmail(email);
        if (user==null){
            throw new UserNotFoundException("User not found ");
        }
        String userEmail = user.getEmail();
        int otp = OTPUtil.otp();
        log.info("OTP {}", otp);
        emailSendarUtil.sendEmailWithMultipleBodyLine(userEmail, Arrays.asList("Dear " + user.getFirstName() + " " + user.getLastName() + ",",
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
        user.setOtpSendingTime(LocalDateTime.now());
        user.setOtpExpireTime(LocalDateTime.now().plusMinutes(15));
        user.setOtp(otp);
        userRepo.saveAndFlush(user);
        return "OTP send successfully !";
    }

    @Override
    public UserResponseDTO findByEmail(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found: " + email);
        }
        if (!user.isVerified()){
            throw new UnverifiedUserException("User not verified: " + email);
        }
        return UserConvertor.convertUserToUserResponseDTO(user);
    }

    @Override
    public String deleteUser(Long id) {
        User user = findUserById(id);
        userRepo.delete(user);
        return "User Deleted Successfully !";
    }

    @Override
    public String forgetPassword(String email) {
        User user = userRepo.findByEmail(email);
        if (user==null){
            throw new UserNotFoundException("User not found for the email " + email);
        }
        if (!user.isVerified()){
            throw new UnverifiedUserException("user is not verified , please verify yourself first ");
        }
        int OTP = OTPUtil.otp();
        log.info("OTP {}" , OTP);
        user.setOtp(OTP);
        emailSendarUtil.sendEmailWithMultipleBodyLine(user.getEmail() ,  Arrays.asList("Dear " + user.getFirstName() + " " + user.getLastName() +
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
        userRepo.saveAndFlush(user);
        return "please enter the otp sent to your email and the new password you want to change";
    }

    @Override
    public String changePassword(ChangePasswordDTO changePasswordDTO) {
        if (changePasswordDTO.getOtp()==null || changePasswordDTO.getPassword()==null) {
            throw new InvalidOTPException("OTP or Password cannot be null , please enter both the field");
        }
        User user = userRepo.findByOTP(changePasswordDTO.getOtp());
        if (user==null){
            throw new InvalidOTPException("Incorrect OTP");
        }
        Integer otp = user.getOtp();
        if (!otp.equals(changePasswordDTO.getOtp())){
            throw new InvalidOTPException("Incorrect OTP");
        }
        user.setPassword(changePasswordDTO.getPassword());
        user.setOtp(null);
        userRepo.saveAndFlush(user);
        return "Password change successfully !";
    }

    @Override
    public UserResponseDTO findUserByUserId(Long id) {
        User user = findUserById(id);
        return UserConvertor.convertUserToUserResponseDTO(user);
    }

    @Override
    public String adminUser(UserDTO userDTO) {
        Set<Roles> rolesSet = new HashSet<>();
        Roles roles = rolesRepository.findByName("ROLE_USER").get();
        rolesSet.add(roles);
        User user = UserConvertor.adminUserRegistration(userDTO);
        user.setRoles(rolesSet);
        UserRegisterationEvent userRegisterationEvent = new UserRegisterationEvent(user);
        applicationEventPublisher.publishEvent(userRegisterationEvent);
        userRepo.save(user);
        return "User Registered SuccessFully !";
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty()){
            throw new UserNotFoundException("No user exist in the db");
        }
        return users;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepo.findByEmail(email);
        if(user==null){
            throw new UserNotFoundException("User not exist with the email " + email);
        }
        return user;
    }

    @Override
    public String bulkDelete() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty()){
            throw new UserNotFoundException("No user found in the db");
        }
        userRepo.deleteAll();
        return "All user deleted successfully !";
    }

    @Override
    public String blockUserAccount(Long id) {
        User user = userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("No User found for the id " + id));
        user.setVerified(false);
        userRepo.save(user);
        return "User account has  blocked successfully !";
    }

    @Override
    public User findUserByReferralCode(String referralCode) {
        User user = userRepo.findUserByReferralCode(referralCode);
        if (user==null){
            throw new UserNotFoundException("Invalid referral code ");
        }
        return user;
    }

    @Override
    public int getApiHitCount(String email) {
        return userRepo.getUserApiHitCount(email);
    }

    @Override
    public Instant getUserApiHittingTargetTime(String email) {
        return userRepo.findUserApiHittingTargetTime(email);
    }

    @Override
    public String rateDeliveryGuy(Authentication authentication, DeliveryGuyRatingDTO ratingDTO) {
        User user = commonUtil.authenticatedUser(authentication);
        Optional<OrderConfirmationDetails> orderConfirmationDetails = orderConfirmationRepo.findById(ratingDTO.getOrderId());
        if (orderConfirmationDetails.isEmpty()){
            throw new DeliveryGuyException("No Order found for the orderId " + ratingDTO.getOrderId());
        }
        DeliveryGuy deliveryGuy = orderConfirmationDetails.get().getDeliveryGuy();
        DeliveryGuyRating deliveryGuyRating = DeliveryGuyReviewAndRatingMapper.INSTANCE.convertDeliveryGuyRatingDTOToDeliveryGuyRating(ratingDTO);
        deliveryGuyRating.setUser(user);
        deliveryGuyRating.setDeliveryGuy(deliveryGuy);
        deliveryGuyRating.setOrderId(orderConfirmationDetails.get().getId());
        deliveryGuyRatingRepo.save(deliveryGuyRating);

        return "Rating successfully !!";
    }

    @Override
    public User findByEmailOrUsername(String email, String username) {

        Optional<User> user = userRepo.findByUsernameOrEmail(email,username);
        if (user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return user.get();
    }

//    @Override
//    public User findUserByUsername(String username) {
//        return null;
//    }

    @Override
    public User findUserByUsername(String username) {
        User user = userRepo.findUserByUsername(username);
        if(user==null){
            throw new UserNotFoundException("User not exist with the username " + username);
        }
        return user;
    }


}
