package com.foodDelivaryApp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank
    @Size(min = 3, max = 40, message = "Invalid Username [username should be b/w 3-40 characters]")
    private String username;

    @Email
    @NonNull
    @Size(min = 8, max = 60, message = "Invalid Email [email should be b/w 8-60 characters]")
    private String email;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 60, message = "Invalid Password [password should be 5-60 characters]")
    private String password;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 30, message = "Invalid firstName [firstName should be 5-30 characters]")
    private String firstName;

    @Size(max = 30, message = "Invalid lastName [lastName should be maximum 30 characters]")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime dateOfBirth;

    @Size(max = 15, message = "Invalid phoneNumber [phoneNumber should be maximum 15 characters]")
    private String phoneNumber;

    @Size(min = 5 ,max = 100, message = "Invalid address [address should be maximum 100 characters]")
    private String address;

    @Size(min = 3 , max = 50, message = "Invalid city [city should be maximum 50 characters]")
    private String city;

    @Size(min = 2 , max = 50, message = "Invalid state [state should be maximum 50 characters]")
    private String state;

    @Size(min = 2 ,max = 5, message = "Invalid country [country should be maximum 5 characters]")
    private String country;

    @Size(min = 5 ,max = 10, message = "Invalid postalCode [postalCode should be b/w 5-10 characters]")
    private String postalCode;
    private String profilePicture;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isVerified;
    private boolean isActive;
    private String preferredLanguage;


}
