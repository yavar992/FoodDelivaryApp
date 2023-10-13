package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.UserUpdateDTO;
import com.foodDelivaryApp.userservice.DTO.VerifyOTP;
import com.foodDelivaryApp.userservice.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    String saveUser(User user);

    boolean userAlreadyExistByEmailOrUserName(String email, String username);

    String uploadImages(Long id, MultipartFile file) throws IOException;

    User findUserById(Long id);

    String updateImage(Long id, MultipartFile file) throws IOException;

    User updateUser(Long id, UserUpdateDTO userUpdateDTO);

    String verifyUserAccount(VerifyOTP verifyOTP);

    String resendOTP(String email);
}
