package com.foodDelivaryApp.userservice.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UserDTO {


    @NotBlank
    @NotNull
    @Size(min = 3, max = 40, message = "Invalid Username [username should be b/w 3-40 characters]")
    private String username;

    @Email
    @NotNull
    @Size(min = 8, max = 60, message = "Invalid Email [email should be b/w 8-60 characters]")
    @Column(unique = true , nullable = false)
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30, message = "Invalid firstName [firstName should be 5-30 characters]")
    private String firstName;

    @Size(max = 30, message = "Invalid lastName [lastName should be maximum 30 characters]")
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private String countryCode;
    @Size(max = 15, message = "Invalid phoneNumber [phoneNumber should be maximum 15 characters]")
    private String phoneNumber;
    @Size(min = 5 ,max = 100, message = "Invalid address [address should be maximum 100 characters]")
    private String address;
    @Size(min = 3 , max = 50, message = "Invalid city [city should be maximum 50 characters]")
    private String city;
    @Size(min = 2 , max = 50, message = "Invalid state [state should be maximum 50 characters]")
    private String state;
    @Size(min = 2 , max = 15, message = "Invalid country [country should be maximum 15 characters]")
    private String country;
    @Size(min = 5 ,max = 10, message = "Invalid postalCode [postalCode should be b/w 5-10 characters]")
    private String postalCode;

}
