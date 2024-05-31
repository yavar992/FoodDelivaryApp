package com.foodDelivaryApp.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@DynamicUpdate
public class OrderConfirmationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // item name
        private Long quantity; // item quantity
    private double price; // total price --will convert this into th dollar
    private String orderNumber; // auto generated Number for the order
    private LocalDate orderDate; // date
    @OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<MenuItem> menuItems; // menuItemId
    private double totalAmountPaid; // total amount paid
    private String deliveryTimeEstimate; // further task
    @OneToOne(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_Address_id" )
    private Address defaultDeliveryAddress; // user default address
    private String paymentMethod;  // paypal
    private String contactPhone; //restaurant contact number for further inquiry
    private String contactEmail; // restaurant email for the further enquiry
    private String trackingUrl; // will do this once i'll implement the google api for the tracking of the user

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
