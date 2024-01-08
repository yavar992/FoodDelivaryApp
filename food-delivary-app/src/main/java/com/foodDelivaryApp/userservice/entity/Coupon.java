package com.foodDelivaryApp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Timestamp;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Coupon {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Coupon code is required")
    private String code;

    private int discount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate expirationPeriod;

    private int couponStock;
    private int purchaseLimitAmount;
    private LocalDate couponCreatedTime;
    private LocalDate couponModifiedTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private RestaurantOwner restaurantOwner;

    private boolean isDeleted;
    private Boolean active;


}
