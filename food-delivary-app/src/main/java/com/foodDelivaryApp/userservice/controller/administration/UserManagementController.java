package com.foodDelivaryApp.userservice.controller.administration;


import com.foodDelivaryApp.userservice.DTO.UserDTO;
import com.foodDelivaryApp.userservice.DTO.UserUpdateDTO;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealConstant;
import com.foodDelivaryApp.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/users")
public class UserManagementController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO){
        try {
            String userRegistrationMessage = userService.adminUser(userDTO);
            if (userRegistrationMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(userRegistrationMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }


    @GetMapping
    public ResponseEntity<?> getAllUser(){
        try {
            List<User> users = userService.findAllUsers();
            if (!users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(users);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateUsers(@PathVariable("id") Long id , @RequestBody UserUpdateDTO userUpdateDTO){
        try {
            User updateUser = userService.updateUser(id , userUpdateDTO);
            if (updateUser!=null){
                return ResponseEntity.status(HttpStatus.OK).body("User Updated Successfully !");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        try {
            User user = userService.findUserByEmail(email);
            if (user!=null) {
//                log.info("User {}" , user);
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @DeleteMapping("/delete/{id}")
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


    @DeleteMapping("/bulkDelete")
    public ResponseEntity<?> deleteAllUser(){
        try {
            String deletedMessage = userService.bulkDelete();
            if (deletedMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(deletedMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @PostMapping("/block/{id}")
    public ResponseEntity<?> blockUserAccount(@PathVariable("id") Long id){
        try {
            String deletedMessage = userService.blockUserAccount(id);
            if (deletedMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(deletedMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

}
