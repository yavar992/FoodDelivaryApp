package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.UserDTO;
import com.foodDelivaryApp.userservice.DTO.VerifyOTP;
import com.foodDelivaryApp.userservice.convertor.UserConvertor;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth/")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO){
        try {
            if (userService.userAlreadyExistByEmailOrUserName(userDTO.getEmail() , userDTO.getUsername())){
                return ResponseEntity.status(HttpStatus.OK).body("User is already register with the same email or username");
            }

            User user = UserConvertor.convertUserDtoToUserEntity(userDTO);
//            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            String registrationMessage =  userService.saveUser(user);
            if (registrationMessage!=null){
                return ResponseEntity.status(HttpStatus.CREATED).body(registrationMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot Register the User due to Invalid request");
    }



    @PostMapping("/verifyOtp")
    public  ResponseEntity<?> verifyOtp(@RequestBody VerifyOTP verifyOTP){
        try {
            String verifyUser = userService.verifyUserAccount(verifyOTP);
            if (verifyUser!=null){
                return ResponseEntity.status(HttpStatus.OK).body(verifyUser);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot verify OTP with id  due to internal server error");
    }

    @GetMapping("/resendOtp")
    public ResponseEntity<?> resendOTP(@RequestParam("email") String email){
        try {
            String successMessage = userService.resendOTP(email);
            if (successMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(successMessage);          }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot resend OTP with id due to internal server error");
    }

    //CHANGE PASSWORD
    @GetMapping("/hello")
    public String hi(){
        return "this is test api";
    }


}
