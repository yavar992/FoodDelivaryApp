package com.foodDelivaryApp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String countryCode;

    private String phoneNumber;

    private String address;

    private String city;

    private String state;

    private String country;

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
    private String preferredLanguage;
    private Integer otp;
    private LocalDateTime otpSendingTime;
    private LocalDateTime otpExpireTime;
    private boolean isBlocked;
    private int apiHitCount;
    private Instant firstTimeApiHittingTime;
    private Instant targetTime;


    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Cart cart;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade =CascadeType.ALL)
    private Wallet wallet;

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_referrals",
            joinColumns = @JoinColumn(name = "guyWhoSignup_id"),
            inverseJoinColumns = @JoinColumn(name = "guyWhoReferrerCode_id")
    )
    private Set<User> referredUsers;


    @JsonIgnore
    @ToString.Exclude
    @OneToMany( cascade = CascadeType.ALL , fetch = FetchType.LAZY , orphanRemoval = true)
    private List<Address> addresses;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "defaultAddress_id")
    private Address defaultAddress;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL )
    @JsonIgnore
    private Wishlist wishlist;

    @OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<DeliveryGuyRating> deliveryGuyRating;

    @OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<OrderConfirmationDetails> orderConfirmationDetails;



}
