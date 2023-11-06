package com.foodDelivaryApp.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private Double price;
    private String description;
    private Double shippingCharge;
    private Double tax;
    private Double total;
    private String referenceNumber; // random reference number using UUID
    private LocalDateTime createdTime;
    private String paymentStatus;
    private String intent;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

