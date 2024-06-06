package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.DeliveryGuyProfileDTO;
import com.foodDelivaryApp.userservice.DTO.DeliveryGuyRatingDTO;
import com.foodDelivaryApp.userservice.DTO.ShiftDTO;

import com.foodDelivaryApp.userservice.Enums.DeliveryGuyStatusEnum;
import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import com.foodDelivaryApp.userservice.entity.OrderConfirmationDetails;
import com.foodDelivaryApp.userservice.service.DeliveryGuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/deliver")
public class DeliveryGuyController {

    @Autowired
    private DeliveryGuyService deliveryGuyService;

    @Autowired
    private PasswordEncoder passwordEncoder;


//    @PostMapping({"/register","/signup"})
//    public ResponseEntity<?> registerUser(@Valid @RequestBody DeliveryGuyDTO deliveryGuyDTO ,
//                                          @RequestParam(value = "referralCode" , required = false) String referralCode ){
//        try {
//            if (deliveryGuyService.userAlreadyExistByEmailOrUserName(deliveryGuyDTO.getEmail() , deliveryGuyDTO.getUsername())){
//                return ResponseEntity.status(HttpStatus.OK).body("User is already register with the same email or username ");
//            }
//            DeliveryGuy deliveryGuy = DeliveryGuyConvertor.convertDeliveryGuyDTOToDeliveryGuy(deliveryGuyDTO);
//            deliveryGuy.setPassword(passwordEncoder.encode(deliveryGuyDTO.getPassword()));
//            String registrationMessage =  deliveryGuyService.saveDeliveryGuy(deliveryGuy,referralCode);
//            if (registrationMessage!=null){
//                return ResponseEntity.status(HttpStatus.CREATED).body(registrationMessage);
//            }
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HappyMealConstant.SOMETHING_WENT_WRONG);
//    }

    @PostMapping("/uploadImage/")
    public ResponseEntity<?> uploadImage(Authentication authentication ,  @RequestParam("image") MultipartFile file){
        try {
            String successMessage = deliveryGuyService.uploadImages(authentication , file);
            if (successMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(successMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot Upload images due to internal server error");
    }

    @PostMapping("/updateProfileImage")
    public ResponseEntity<?> updateProfilePicture(Authentication authentication , @RequestParam("image") MultipartFile file){
        try {
            String imageUpdateMessage = deliveryGuyService.updateImage(authentication , file);
            if (imageUpdateMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(imageUpdateMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("cannot update images due to internal server error");
    }

    @PostMapping("/shiftTime")
    public ResponseEntity<?> chooseShiftTiming(Authentication authentication ,@RequestBody ShiftDTO shiftTO){
      String response =  deliveryGuyService.chooseShiftTiminfOfDeliveyBoy(authentication , shiftTO);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/preferredDeliveryZones")
    public ResponseEntity<?> setPreferredDeliveryZones(
            Authentication authentication ,
            @RequestBody List<String> preferredDeliveryZones) {
        String updatedDeliveryGuy = deliveryGuyService.setPreferredDeliveryZones(authentication, preferredDeliveryZones);
        return ResponseEntity.ok(updatedDeliveryGuy);
    }

    @PostMapping("/{restaurantId}/assignRestaurant")
    public ResponseEntity<?> assignRestaurantToDeliveryGuy(Authentication authentication , @PathVariable Long restaurantId) {
        String updatedDeliveryGuy = deliveryGuyService.assignRestaurantToDeliveryGuy(authentication, restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDeliveryGuy);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(Authentication authentication){
        DeliveryGuyProfileDTO deliveryGuyProfileDTO = deliveryGuyService.getProfile(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyProfileDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDeliveryGuyProfile(Authentication authentication ,  @RequestBody DeliveryGuyProfileDTO updateDTO) {
        DeliveryGuyProfileDTO response = deliveryGuyService.updateDeliveryGuy(authentication , updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDeliveryGuy(Authentication authentication) {
        String response = deliveryGuyService.deleteUser(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @GetMapping("/allDeliverBoy")
    public ResponseEntity<?> listAllDeliveryGuys() {
        List<DeliveryGuyProfileDTO> deliveryGuyProfileDTOS = deliveryGuyService.listAllDeliveryGuys();
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyProfileDTOS);
    }

    @GetMapping("/search")
    public ResponseEntity<DeliveryGuyProfileDTO> searchDeliveryGuy(@RequestParam(required = false) String username, @RequestParam(required = false) String email) {
        DeliveryGuyProfileDTO deliveryGuyProfileDTO = deliveryGuyService.searchDeliveryGuy(username , email);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryGuyProfileDTO);
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateDeliveryGuyStatus(Authentication authentication ,  String statusUpdate) {
        DeliveryGuyStatusEnum status;
        try {
            status = DeliveryGuyStatusEnum.valueOf(statusUpdate.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status value");
        }
         String response = deliveryGuyService.updateStatusOfDeliveryGuy( authentication, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderConfirmationDetails>> getDeliveryGuyOrders(@PathVariable Long id) {
        // Logic to get delivery guy orders
        return ResponseEntity.status(HttpStatus.OK).body(Collections.EMPTY_LIST);
    }

    @PostMapping("/requestRating/{userEmail}")
    public ResponseEntity<?> requestRatingFromUser(@PathVariable String userEmail) {
        // Logic to handle rating request from delivery guy's dashboard
        return ResponseEntity.status(HttpStatus.OK).body("");
    }


    @GetMapping
    public String hi(){
        return "hello this is test api";
    }

}
