package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.DTO.UserDTO;
import com.foodDelivaryApp.userservice.DTO.UserUpdateDTO;
import com.foodDelivaryApp.userservice.entity.User;
import java.time.LocalDateTime;

public class UserConvertor {

    public static User convertUserDtoToUserEntity(UserDTO userDTO){
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .dateOfBirth(userDTO.getDateOfBirth())
                .countryCode(userDTO.getCountryCode())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .city(userDTO.getCity())
                .state(userDTO.getState())
                .country(userDTO.getCountry())
                .postalCode(userDTO.getPostalCode())
                .createdAt(LocalDateTime.now())
                .preferredLanguage("english")
                .isActive(true)
                .build();
    }
    public static void updateUser(User user, UserUpdateDTO userUpdateDTO) {
        user.setUsername(userUpdateDTO.getUsername());
        user.setEmail(userUpdateDTO.getEmail());
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setDateOfBirth(userUpdateDTO.getDateOfBirth());
        user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        user.setAddress(userUpdateDTO.getAddress());


    }


}
