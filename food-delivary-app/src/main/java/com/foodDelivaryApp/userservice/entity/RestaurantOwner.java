package com.foodDelivaryApp.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RestaurantOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name cannot be null.")
    @Size(min = 2, max = 32, message = "First name must be between 2 and 32 characters long.")
    private String firstName;

    @NotNull(message = "Last name cannot be null.")
    @Size(min = 2, max = 32, message = "Last name must be between 2 and 32 characters long.")
    private String lastName;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Invalid email address.")
    private String email;

    @NotNull
    private String countryCode;

    @NotNull(message = "Phone number cannot be null.")
    private String phoneNumber;

    @NotNull(message = "Address cannot be null.")
    private String address;

    @NotNull(message = "City cannot be null.")
    private String city;

    @NotNull(message = "State cannot be null.")
    private String state;

    @NotNull(message = "Zip code cannot be null.")
    private String zipCode;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters long.")
    private String password;

    private Boolean isVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer otp;
    private LocalDateTime otpSendingTime;
    private LocalDateTime otpExpireTime;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY )
    private List<Restaurant> restaurant;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles" ,
            joinColumns =@JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private Set<Roles> roles;


}
