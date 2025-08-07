package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.DTO.UserDTO;
import com.foodDelivaryApp.userservice.DTO.UserResponseDTO;
import com.foodDelivaryApp.userservice.DTO.UserUpdateDTO;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.util.GeneratedRandomNumber;

import java.time.LocalDateTime;

public interface UserConvertor {


    static User convertUserDtoToUserEntity(UserDTO userDTO){
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .password(userDTO.getPassword())
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
                .createdBy("SELF_REGISTRATION")
                .referralCode(GeneratedRandomNumber.generateReferralCode(6))
                .preferredLanguage("english")
                .isActive(true)
                .build();

     }

     static void updateUser(User user, UserUpdateDTO userUpdateDTO) {
        user.setUsername(userUpdateDTO.getUsername());
        user.setEmail(userUpdateDTO.getEmail());
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setDateOfBirth(userUpdateDTO.getDateOfBirth());
        user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        user.setAddress(userUpdateDTO.getAddress());
        user.setUpdatedAt(LocalDateTime.now());
    }

    static UserResponseDTO convertUserToUserResponseDTO(User user){
        return UserResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .city(user.getCity())
                .state(user.getState())
                .country(user.getCountry())
                .address(user.getAddress())
                .postalCode(user.getPostalCode())
                .build();
    }

    static User adminUserRegistration(UserDTO userDTO){
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .password(userDTO.getPassword())
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
                .createdBy("ADMIN")
                .referralCode(GeneratedRandomNumber.generateReferralCode(6))
                .preferredLanguage("english")
                .isActive(true)
                .build();

    }


}
