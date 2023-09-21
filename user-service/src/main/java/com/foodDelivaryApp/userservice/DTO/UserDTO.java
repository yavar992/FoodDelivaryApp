package com.foodDelivaryApp.userservice.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UserDTO {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime dateOfBirth;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private byte[] profilePicture;

}
