package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.convertor.UserConvertor;
import com.foodDelivaryApp.userservice.dto.*;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealConstant;
import com.foodDelivaryApp.userservice.jwt.JwtService;
import com.foodDelivaryApp.userservice.repository.RestaurantsOwnerRepository;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.IRestaurantOwnerService;
import com.foodDelivaryApp.userservice.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    IRestaurantOwnerService restaurantOwnerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RestaurantsOwnerRepository restaurantsOwnerRepo;

    @PostMapping({"/register","/signup"})
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO ,
                                          @RequestParam(value = "referralCode" , required = false) String referralCode ){
        try {
            if (userService.userAlreadyExistByEmailOrUserName(userDTO.getEmail() , userDTO.getUsername())){
                return ResponseEntity.status(HttpStatus.OK).body("User is already register with the same email or username ");
            }
            User user = UserConvertor.convertUserDtoToUserEntity(userDTO);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            String registrationMessage =  userService.saveUser(user,referralCode);
            if (registrationMessage!=null){
                return ResponseEntity.status(HttpStatus.CREATED).body(registrationMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }



    @PostMapping("/verifyOtp")
    public  ResponseEntity<?> verifyOtp(@RequestBody VerifyOTP verifyOTP){
            // String verifyUser = userService.verifyUserAccount(verifyOTP);
            return ResponseEntity.status(HttpStatus.OK).body(userService.verifyUserAccount(verifyOTP));
    }

        @GetMapping("/resendOtp")
       public ResponseEntity<?> resendOTP(@RequestParam("email") String email){
            // String successMessage = userService.resendOTP(email);
                return ResponseEntity.status(HttpStatus.OK).body(userService.resendOTP(email));        
            }
    

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(Authentication authentication){
        try {
            String username = authentication.getName();
            User user = userRepo.findByEmail(username);
            String forgetPasswordMessage = userService.forgetPassword(user.getEmail());
            if (forgetPasswordMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(forgetPasswordMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot reset the password");
    }

    @PostMapping("/changesPassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
    try {
        String changePasswordMessage = userService.changePassword(changePasswordDTO);
        if (changePasswordMessage!=null){
            return ResponseEntity.status(HttpStatus.OK).body(changePasswordMessage);
        }
    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot change password due to interval server error");

    }



    @PostMapping("/restaurant-owner")
    public ResponseEntity<?> registerRestaurantOwner(@Valid  @RequestBody RestaurantOwnerDTO restaurantOwnerDTO){
       try {
           if (restaurantOwnerService.ownerExistByEmail(restaurantOwnerDTO.getEmail())){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is already register with the same email , plz try again with a new email");
           }
           String signupMessage = restaurantOwnerService.saveRestaurantOwner(restaurantOwnerDTO);
           if (signupMessage!=null){
               return ResponseEntity.status(HttpStatus.CREATED).body(signupMessage);
           }
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot register Restaurant Owner due to invalid request");
    }

    @PostMapping("/verifyRestaurantOwner")
    public ResponseEntity<?> verifyRestaurantOwner(@RequestBody VerifyOTP verifyOTP){
        try {
            String userVerfiyMessage = restaurantOwnerService.verifyRestaurantOwner(verifyOTP);
            if (userVerfiyMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(userVerfiyMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot verify the user due to invalid request !");
    }

    @PostMapping("/requestNewOTP")
    public ResponseEntity<?> resendOTPToRestaurantOwner(@RequestParam("email") String email){
        try {
            String resendOTPMessage = restaurantOwnerService.resendOTP(email);
            if (resendOTPMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(resendOTPMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot send the otp due to invalid request");
    }


    @PostMapping("restaurantOwner/forget-password")
    public ResponseEntity<?> forgetPasswordForRestaurantOwner(@RequestBody ForgetPasswordDTO forgetPasswordDTO){
        try {
            String forgotPasswordMessage = restaurantOwnerService.forgetPassword(forgetPasswordDTO.getEmail());
            if (forgotPasswordMessage!=null) {
                return ResponseEntity.status(HttpStatus.OK).body(forgotPasswordMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Request");
    }


    @PostMapping("restaurant/changesPassword")
    public ResponseEntity<?> changePasswordForRestaurantOwner(@RequestBody ChangePasswordDTO changePasswordDTO){
        try {
            String changePasswordMessage = restaurantOwnerService.changePassword(changePasswordDTO);
            if (changePasswordMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(changePasswordMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot change password due to interval server error");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO)  {
        User user = userRepo.findByEmail(authDTO.getUsername());
        if (user ==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email is not register plz register yourself first to login ");
        }
        boolean isVerified = user.isVerified();
        if (!isVerified){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please verify your account first in order to login");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
       if (!authentication.isAuthenticated()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials");
       }
       String jwtToken = jwtService.generateToken(authDTO.getUsername());
       return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }

    @GetMapping("/helloUser")
    public String helloUser(Principal principal){
        return "Hello " +  principal.getName();
    }



}
