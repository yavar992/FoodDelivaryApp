package com.foodDelivaryApp.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@DynamicUpdate
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingId;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToOne
    @JoinColumn(name = "cartItem_id")
    private CartItem cartItem;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @OneToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;


    private int count;

    @Enumerated(EnumType.STRING)
    private PaymentMethodAcceptedEnum paymentMethodAccepted;

    // Payment Information
    private String transactionId;
    @Enumerated(EnumType.STRING)
    private CheckoutStatusEnum paymentStatus;
    private LocalDateTime paymentDate;

    // Order Details
    private int itemCount;  //no of item for checkout
    private Double totalAmount;  // totalAmount
    private Double discountAmount;  // Amount of discount after applying coupon or offer
    private Double shippingCharge; // shipping charge
    private Double amountToPay; //amount to pay after applying all the coupon or offer



    // User Information
    private String phoneNumber;
    private String email;


    // Audit and Tracking
    private LocalDateTime creationDate;  // checkout date and time
    private LocalDateTime lastModifiedDate; // checkout time on modification
    @OneToOne
    private User createdBy;  // user who will checkout the order
    @OneToOne
    private User updatedBy; // user or admin who will update the checkout

    // Status and Workflow
    @Enumerated(EnumType.STRING)
    private CheckoutStatusEnum status;
    @Enumerated(EnumType.STRING)
    private PaymentIntentEnum paymentIntent;

    // Miscellaneous
    private String promoCode;
    private LocalDateTime deliveryDate;
    @Enumerated(EnumType.STRING)
    private DeliveryMethodEnum deliveryMethod;





}
