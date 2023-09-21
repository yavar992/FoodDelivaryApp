package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.DTO.UserDTO;
import com.foodDelivaryApp.userservice.entity.User;

import java.time.LocalDateTime;
import java.util.Arrays;

public class UserConvertor {

    public static User convertUserDtoToUserEntity(UserDTO userDTO){
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .dateOfBirth(userDTO.getDateOfBirth())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .city(userDTO.getCity())
                .state(userDTO.getState())
                .country(userDTO.getCountry())
                .postalCode(userDTO.getPostalCode())
                .profilePicture(Arrays.toString(userDTO.getProfilePicture()))
                .role("USER")
                .createdAt(LocalDateTime.now())
                .preferredLanguage("english")
                .isActive(true)
                .build();
    }
    
}
