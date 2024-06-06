package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.ChangePasswordDTO;
import com.foodDelivaryApp.userservice.DTO.DeliveryGuyProfileDTO;
import com.foodDelivaryApp.userservice.DTO.ShiftDTO;
import com.foodDelivaryApp.userservice.DTO.VerifyOTP;
import com.foodDelivaryApp.userservice.Enums.DeliveryGuyStatusEnum;
import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DeliveryGuyService {
    boolean userAlreadyExistByEmailOrUserName(String email, String username);

    String saveDeliveryGuy(DeliveryGuy deliveryGuy, String referralCode);

    Object verifyUserAccount(VerifyOTP verifyOTP);

    Object resendOTP(String email);

    DeliveryGuy findByDeliveryEmail(String email);

    String resendOTPToDeliveryGuy(DeliveryGuy deliveryGuy);

    String forgetPassword(String email);

    String changePassword(ChangePasswordDTO changePasswordDTO);

    String uploadImages(Authentication  authentication, MultipartFile file) throws IOException;

    String updateImage(Authentication authentication, MultipartFile file) throws IOException;

    String chooseShiftTiminfOfDeliveyBoy(Authentication authentication, ShiftDTO shiftDTO);

    String setPreferredDeliveryZones(Authentication authentication, List<String> preferredDeliveryZones);

    String assignRestaurantToDeliveryGuy(Authentication authentication, Long restaurantId);

    DeliveryGuyProfileDTO getProfile(Authentication authentication);

    DeliveryGuyProfileDTO updateDeliveryGuy(Authentication authentication, DeliveryGuyProfileDTO updateDTO);

    String deleteUser(Authentication authentication);

    List<DeliveryGuyProfileDTO> listAllDeliveryGuys();

    DeliveryGuyProfileDTO searchDeliveryGuy(String username, String email);

    String updateStatusOfDeliveryGuy(Authentication authentication, DeliveryGuyStatusEnum statusUpdate);
}
