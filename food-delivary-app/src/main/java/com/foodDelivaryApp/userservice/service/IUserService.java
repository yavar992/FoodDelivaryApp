package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.dto.*;
import com.foodDelivaryApp.userservice.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    String saveUser(User user , String referralCode);

    boolean userAlreadyExistByEmailOrUserName(String email, String username);

    String uploadImages(long id, MultipartFile file) throws IOException;

    User findUserById(long id);

    String updateImage(long id, MultipartFile file) throws IOException;

    User updateUser(long id, UserUpdateDTO userUpdateDTO);

    String verifyUserAccount(VerifyOTP verifyOTP);

    String resendOTP(String email);

    UserResponseDTO findByEmail(String email);

    String deleteUser(long id);

    String forgetPassword(String email);

    String changePassword(ChangePasswordDTO changePasswordDTO);

    UserResponseDTO findUserByUserId(long id);

    String adminUser(UserDTO userDTO);

    List<User> findAllUsers();

    User findUserByEmail(String email);

    String bulkDelete();

    String blockUserAccount(long id);

    User findUserByReferralCode(String referralCode);

}
