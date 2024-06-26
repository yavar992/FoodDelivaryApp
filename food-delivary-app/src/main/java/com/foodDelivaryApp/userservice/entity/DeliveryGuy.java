package com.foodDelivaryApp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodDelivaryApp.userservice.Enums.DeliveryGuyStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Entity
public class DeliveryGuy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Size(min = 2 ,max = 5, message = "Invalid country [country should be maximum 5 characters]")
    private String country;

    @Size(min = 5 ,max = 10, message = "Invalid postalCode [postalCode should be b/w 5-10 characters]")
    private String postalCode;
    @JsonIgnore
    private String fileName;
    @JsonIgnore
    private String fileType;
    @JsonIgnore
    @Lob
    @Column(name="imageData", length = 1000)
    private byte[] profilePicture;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String referralCode;
    private boolean isVerified;
    private boolean isActive;
    private Integer otp;
    private LocalDateTime otpSendingTime;
    private LocalDateTime otpExpireTime;
    private boolean isBlocked;

    @Enumerated(EnumType.STRING)
    private DeliveryGuyStatusEnum status; // ACTIVE, INACTIVE, ON_DELIVERY
    private String vehicleType;
    private String licenseNumber;
    private Integer totalDeliveries;
    private Double currentLatitude;
    private Double currentLongitude;
    private String shiftStart;
    private String shiftEnd;
    private String preferredDeliverZones;
    private int apiHitCount;
    private Instant firstTimeApiHittingTime;
    private Instant targetTime;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(name = "deliveryBoy_roles" ,
            joinColumns =@JoinColumn(name = "deliveryBoy_id", referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private Set<Roles> roles;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "deliver_referrals",
            joinColumns = @JoinColumn(name = "guyWhoSignup_id"),
            inverseJoinColumns = @JoinColumn(name = "guyWhoReferrerCode_id")
    )
    private Set<DeliveryGuy> referredDeliveryGuy;


    @OneToMany(mappedBy = "deliveryGuy" , fetch = FetchType.EAGER , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<OrderConfirmationDetails> orderConfirmationDetails;

    @OneToMany( mappedBy =  "deliveryGuy" ,fetch = FetchType.LAZY , cascade =CascadeType.ALL , orphanRemoval = true)
    private List<DeliveryGuyRating> deliveryGuyRating;

    @ManyToMany(mappedBy = "deliveryGuys")
    private Set<Restaurant> restaurants = new HashSet<>();



}
