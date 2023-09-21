package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.UserDTO;
import com.foodDelivaryApp.userservice.convertor.UserConvertor;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO){
        try {
            if (userService.userAlreadyExistByEmailOrUserName(userDTO.getEmail() , userDTO.getUsername())){
                return ResponseEntity.status(HttpStatus.OK).body("User is already with the same email or username");
            }
            User user = UserConvertor.convertUserDtoToUserEntity(userDTO);
            String registrationMessage =  userService.saveUser(user);
            if (registrationMessage!=null){
                return ResponseEntity.status(HttpStatus.CREATED).body(registrationMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot Register the User due to Invalid request");
    }


}
