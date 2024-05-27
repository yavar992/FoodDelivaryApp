package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.*;
import com.foodDelivaryApp.userservice.convertor.UserConvertor;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealConstant;
import com.foodDelivaryApp.userservice.jwt.JWTBlacklistService;
import com.foodDelivaryApp.userservice.jwt.JwtService;
import com.foodDelivaryApp.userservice.jwt.RefreshToken;
import com.foodDelivaryApp.userservice.service.RestaurantOwnerService;
import com.foodDelivaryApp.userservice.service.UserService;
import com.foodDelivaryApp.userservice.serviceImpl.RefreshTokenService;
import com.foodDelivaryApp.userservice.util.CommonUtil;
import com.foodDelivaryApp.userservice.util.LoginRateLimitApiUtil;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private LoginRateLimitApiUtil loginRateLimitApiUtil;

    @Autowired
    private JWTBlacklistService jwtBlacklistService;

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
           User user = commonUtil.authenticatedUser(authentication);
           loginRateLimitApiUtil.handleApiHitCount(user);
            // Check if the user has exceeded the maximum number of API hits without entering OTP
            if (user.isBlocked() && user.getTargetTime().isAfter(Instant.now())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account is blocked for the next 15 minutes due to multiple unsuccessful attempts.");
            }

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
        User user = userService.findUserByEmail(authDTO.getUsername());
        boolean isVerified = user.isVerified();
        if (!isVerified){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please verify your account first in order to login");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
       if (!authentication.isAuthenticated()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials");
       }

       RefreshToken refreshToken =  refreshTokenService.createRefreshToken(authDTO.getUsername());
//       String jwtToken = jwtService.generateToken(authDTO.getUsername());
       JWTResponseTokenDTO jwtToken = JWTResponseTokenDTO.builder().accessToken(jwtService.generateToken(authDTO.getUsername()))
               .refreshToken(refreshToken.getToken()).build();
       return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken());
        refreshTokenService.verifyExpiration(refreshToken);
        User user = refreshToken.getUser();
        String token = jwtService.generateToken(user.getUsername());
        JWTResponseTokenDTO jwtResponseTokenDTO = JWTResponseTokenDTO.builder()
                .accessToken(token)
                .refreshToken(refreshTokenRequestDTO.getRefreshToken())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponseTokenDTO);

    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody JWTResponseTokenDTO jwtResponseTokenDTO) {
        // Invalidate the refresh token
        refreshTokenService.invalidateRefreshToken(jwtResponseTokenDTO.getRefreshToken());

//         Blacklist the access token
        String accessToken = jwtResponseTokenDTO.getAccessToken();
        LocalDateTime expiryTime = LocalDateTime.ofInstant(jwtService.extractExpiration(accessToken).toInstant(), ZoneId.systemDefault());
        jwtBlacklistService.blacklistToken(accessToken, expiryTime);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully logged out");
    }


    @GetMapping("/helloUser")
    public String helloUser(Principal principal){
        return "Hello " +  principal.getName();
    }



}
