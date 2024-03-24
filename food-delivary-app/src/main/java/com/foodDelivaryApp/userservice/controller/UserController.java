package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.dto.UserResponseDTO;
import com.foodDelivaryApp.userservice.dto.UserUpdateDTO;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.exceptionHandling.UserNotFoundException;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealConstant;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.IUserService;
import com.foodDelivaryApp.userservice.util.LocalDateTypeAdaptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable("id") Long id , @RequestParam("image") MultipartFile file){
        try {
            String successMessage = userService.uploadImages(id , file);
            if (successMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(successMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot Upload images due to internal server error");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> uploadImages(@PathVariable("id") Long id){
        try {
            User user = userService.findUserById(id);
            if (user!=null){
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the User due to internal server error");
    }


    @PostMapping("/updateProfileImage/{id}")
    public ResponseEntity<?> updateProfilePicture(@PathVariable("id") Long id ,  @RequestParam("image") MultipartFile file){
        try {
            String imageUpdateMessage = userService.updateImage(id, file);
            if (imageUpdateMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(imageUpdateMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("cannot update images due to internal server error");
    }

    @PostMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUserDetails(@Valid @PathVariable("id") Long id , @RequestBody UserUpdateDTO userUpdateDTO){
        try {
            User updateUser = userService.updateUser(id , userUpdateDTO);
            if (updateUser!=null){
                return ResponseEntity.status(HttpStatus.OK).body("User Updated Successfully !");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot update user details due to internal server error");
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@Valid  @RequestBody UserUpdateDTO userUpdateDTO , Authentication authentication){
        try {
            if (authentication == null){
                throw new UserNotFoundException("Please login first");
            }
            if (authentication.isAuthenticated()){
                String username = authentication.getName();
                User user = userService.findUserByEmail(username);
                Long id = user.getId();
                User updateUser = userService.updateUser(id , userUpdateDTO);
                if (updateUser!=null){
                    return ResponseEntity.status(HttpStatus.OK).body("User Updated Successfully !");
                }
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot update user details due to internal server error");
    }

    @GetMapping("getUserByEmail/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        try {
            UserResponseDTO user = userService.findByEmail(email);
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class , new LocalDateTypeAdaptor()).create();
            String json = gson.toJson(user);
            if (user!=null) {
                log.info("User {}" , user);
                return ResponseEntity.status(HttpStatus.OK).body(json);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id){
        try {
            String deletedMessage = userService.deleteUser(id);
            if (deletedMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(deletedMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(Authentication authentication){
        try {
            if (authentication==null){
                throw new LoginException("Unauthorized User ");
            }
            if (authentication.isAuthenticated()){
                String username = authentication.getName();
                User user = userService.findUserByEmail(username);
                userRepo.delete(user);
                return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully !");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }


//    @PostMapping("/post")
//    public String hh(){
//        return "post request is working";
//    }
//
//    @DeleteMapping("/delete")
//    public String de(){
//        return "delete request also working";
//    }
//
//    @PostMapping("/pp")
//    public String as(){
//        return "this is second post request";
//    }
//
//    @DeleteMapping("/dd")
//    public String sd(){
//        return "this is second delete request";
//    }
//
//    @GetMapping("/ddd")
//    public String ssd(){
//        return "this is second delete request";
//    }


}
