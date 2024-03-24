package com.foodDelivaryApp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be at most 100 characters long")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    private String pinCode;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be a 10-digit number")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    private String email;

    private String website;

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Cuisine types are required")
    private List<CuisineType> cuisineType;

    @NotBlank(message = "Description is required")
    private String description;

    @Lob
    private List<Byte[]> photos;

    @NotBlank(message = "Unique identifier number is required")
    private String uniqueIdentifierNumber;

    private String hoursOfOperation;

    private List<String> deliveryZones; // or pinCode

    @NotEmpty(message = "Payment methods accepted are required")
    @Enumerated(EnumType.STRING)
    private List<PaymentMethodAccepted> paymentMethodsAccepted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private RestaurantOwner restaurantOwner;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<ReviewAndRating> reviewAndRating;

    @OneToMany(mappedBy = "restaurant" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<RestaurantMenu> restaurantMenu;


}
