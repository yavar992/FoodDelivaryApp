package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.*;
import com.foodDelivaryApp.userservice.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

public interface UserService {
    String saveUser(User user , String referralCode);

    boolean userAlreadyExistByEmailOrUserName(String email, String username);

    String uploadImages(Long id, MultipartFile file) throws IOException;

    User findUserById(Long id);

    String updateImage(Long id, MultipartFile file) throws IOException;

    User updateUser(Long id, UserUpdateDTO userUpdateDTO);

    String verifyUserAccount(VerifyOTP verifyOTP);

    String resendOTP(String email);

    UserResponseDTO findByEmail(String email);

    String deleteUser(Long id);

    String forgetPassword(String email);

    String changePassword(ChangePasswordDTO changePasswordDTO);

    UserResponseDTO findUserByUserId(Long id);

    String adminUser(UserDTO userDTO);

    List<User> findAllUsers();

    User findUserByEmail(String email);

    String bulkDelete();

    String blockUserAccount(Long id);

    User findUserByReferralCode(String referralCode);

    int getApiHitCount(String email);

    Instant getUserApiHittingTargetTime(String email);
}
