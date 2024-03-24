package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.dto.ChangePasswordDTO;
import com.foodDelivaryApp.userservice.dto.RestaurantOwnerDTO;
import com.foodDelivaryApp.userservice.dto.VerifyOTP;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;

public interface IRestaurantOwnerService {
    String saveRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO);

    boolean ownerExistByEmail(String email);

    String verifyRestaurantOwner(VerifyOTP verifyOTP);

    String resendOTP(String email);

    String forgetPassword(String email);

    String changePassword(ChangePasswordDTO changePasswordDTO);

    RestaurantOwner findById(Long id);

    RestaurantOwner findByEmail(String email);
}
