package com.foodDelivaryApp.userservice.controller.administration;

import com.foodDelivaryApp.userservice.DTO.AuthDTO;
import com.foodDelivaryApp.userservice.DTO.JWTResponseTokenDTO;
import com.foodDelivaryApp.userservice.DTO.RefreshTokenRequestDTO;
import com.foodDelivaryApp.userservice.DTO.RestaurantDTO;
import com.foodDelivaryApp.userservice.entity.CuisineType;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealConstant;
import com.foodDelivaryApp.userservice.jwt.JwtService;
import com.foodDelivaryApp.userservice.jwt.RefreshToken;
import com.foodDelivaryApp.userservice.repository.RestaurantsOwnerRepo;
import com.foodDelivaryApp.userservice.service.RestaurantsService;
import com.foodDelivaryApp.userservice.serviceImpl.RefreshTokenService;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantsController {

    @Autowired
    private RestaurantsService restaurantsService;

    @Autowired
    private RestaurantsOwnerRepo restaurantsOwnerRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping({"signup/{ownerId}","register/{ownerId}"})
    public ResponseEntity<?> addRestaurant(@PathVariable("ownerId") Long ownerId , @Valid @RequestBody RestaurantDTO restaurantDTO){
        try {
            String successMessage = restaurantsService.addRestaurant(ownerId , restaurantDTO);
            if (successMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(successMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @GetMapping("/allRestaurant")
    public ResponseEntity<?> findAllRestaurant(){
        try {
            List<RestaurantDTO> message = restaurantsService.findAllRestaurant();
            Gson gson = new Gson();
            String json = gson.toJson(message);
            if (message!=null){
                return ResponseEntity.status(HttpStatus.OK).body(json);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @DeleteMapping("/deleteRestaurant/{ownerId}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable("ownerId") Long ownerId ,
                                              @RequestParam("uniqueIdentifierNumber") String uniqueIdentifierNumber){
        try {
            String deleteMessage = restaurantsService.deleteRestaurant(ownerId,uniqueIdentifierNumber);
            if(deleteMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(deleteMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @GetMapping("/getRestaurant/{ownerId}")
    public ResponseEntity<?> getRestaurantByUniqueIdentifierNumber(@PathVariable("ownerId") Long ownerId,
                                                                   @RequestParam("uniqueIdentifierNumber") String uniqueIdentifierNumber){
        try {
            RestaurantDTO restaurantDTO = restaurantsService.getRestaurantByUniqueIdentifierNumber(ownerId , uniqueIdentifierNumber);
            if (restaurantDTO!=null){
                return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @PostMapping("/updateRestaurant/{ownerId}")
    public ResponseEntity<?> updateRestaurant(@PathVariable("ownerId") Long ownerId ,
                                             @RequestParam("uniqueIdentifierNumber") String uniqueIdentifierNumber ,
                                              @RequestBody RestaurantDTO restaurantDTO){
        try {
            String updateMessage = restaurantsService.updateRestaurant(ownerId , uniqueIdentifierNumber , restaurantDTO);
            if (updateMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(updateMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @GetMapping("/cuisineTypes/{ownerId}")
    public ResponseEntity<?> findAllCuisineTypes(@PathVariable("ownerId") Long ownerId ,
                                                 @RequestParam("uniqueIdentifierNumber") String uniqueIdentifierNumber){

        try {
            List<CuisineType> cuisineTypes = restaurantsService.findAllCuisineTypes(ownerId, uniqueIdentifierNumber);
            if (!cuisineTypes.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(cuisineTypes);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @PostMapping("/postApi")
    public String hi(){
        return "this is post api";
    }

    @DeleteMapping("/deleteApi")
    public String jj(){
        return "this is sample delete api";
    }

    @GetMapping("/getApi")
    public String h(){
        return "this is sample get api";
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO){
        RestaurantOwner restaurantOwner = restaurantsOwnerRepo.findByEmails(authDTO.getUsername());
        if (restaurantOwner==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("you don't have any account plz register yourself first !");
        }
        boolean isVerified = restaurantOwner.getIsVerified();
        if (!isVerified){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please verify your account first in order to login");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
        if (!authentication.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials");
        }
        RefreshToken refreshToken =  refreshTokenService.createRefreshTokenForRestaurantOwner(authDTO.getUsername());
        JWTResponseTokenDTO jwtToken = JWTResponseTokenDTO.builder().accessToken(jwtService.generateToken(authDTO.getUsername()))
                .token(refreshToken.getToken()).build();
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequestDTO.getAccessToken());
        refreshTokenService.verifyExpiration(refreshToken);
        RestaurantOwner restaurantOwner = refreshToken.getRestaurantOwner();
        String token = jwtService.generateToken(restaurantOwner.getEmail());
        JWTResponseTokenDTO jwtResponseTokenDTO = JWTResponseTokenDTO.builder()
                .accessToken(token)
                .token(refreshTokenRequestDTO.getAccessToken())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponseTokenDTO);
    }

    @PostMapping("/hello")
    public String jh(){
        return "hello";
    }

}
