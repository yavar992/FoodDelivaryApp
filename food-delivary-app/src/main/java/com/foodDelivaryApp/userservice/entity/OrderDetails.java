package com.foodDelivaryApp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String orderId;
    private String foodCode;
    @Enumerated(EnumType.STRING)
    private PaymentIntentEnum intent;
    private String sku;

    private String upc;


    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private CheckoutStatusEnum orderStatusEnum;


    @ManyToOne
    @JoinColumn(name = "checkout_id")
    private Checkout checkout;

    @OneToOne
    @JoinColumn(name = "manuItem_id")
    private MenuItem menuItem;

    // Item Details
    private Integer quantity;
    private Double discountApplied;



    // Additional Notes
    private String customerNotes;

    // Audit and Tracking
    private LocalDateTime lastUpdatedTime;

    // Miscellaneous
    private String promoCode;
    private boolean isCancelled;


}

