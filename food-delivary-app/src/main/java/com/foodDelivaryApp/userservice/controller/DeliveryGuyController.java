package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.DeliveryGuyDTO;
import com.foodDelivaryApp.userservice.DTO.UserDTO;
import com.foodDelivaryApp.userservice.convertor.DeliveryGuyConvertor;
import com.foodDelivaryApp.userservice.convertor.UserConvertor;
import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealConstant;
import com.foodDelivaryApp.userservice.service.DeliveryGuyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/deliver")
public class DeliveryGuyController {

    @Autowired
    private DeliveryGuyService deliveryGuyService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping({"/register","/signup"})
    public ResponseEntity<?> registerUser(@Valid @RequestBody DeliveryGuyDTO deliveryGuyDTO ,
                                          @RequestParam(value = "referralCode" , required = false) String referralCode ){
        try {
            if (deliveryGuyService.userAlreadyExistByEmailOrUserName(deliveryGuyDTO.getEmail() , deliveryGuyDTO.getUsername())){
                return ResponseEntity.status(HttpStatus.OK).body("User is already register with the same email or username ");
            }
            DeliveryGuy deliveryGuy = DeliveryGuyConvertor.convertDeliveryGuyDTOToDeliveryGuy(deliveryGuyDTO);
            deliveryGuy.setPassword(passwordEncoder.encode(deliveryGuyDTO.getPassword()));
            String registrationMessage =  deliveryGuyService.saveDeliveryGuy(deliveryGuy,referralCode);
            if (registrationMessage!=null){
                return ResponseEntity.status(HttpStatus.CREATED).body(registrationMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @GetMapping
    public String hi(){
        return "hello this is test api";
    }

}
