package com.foodDelivaryApp.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String website;
    private String cuisineType;
    private String description;
    private List<String> photos;
    private String uniqueIdentifierNumber;
    private String hoursOfOperation;
    private String deliveryZones;
    private List<String> paymentMethodsAccepted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private RestaurantOwner restaurantOwner;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , orphanRemoval = true)
    private List<ReviewAndRating> reviewAndRating;

    @OneToOne
    private RestaurantMenu restaurantMenu;


}
