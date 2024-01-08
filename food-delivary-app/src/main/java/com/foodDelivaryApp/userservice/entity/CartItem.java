package com.foodDelivaryApp.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DynamicUpdate
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long quantity;
    private Long menuItemId;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;



    @ManyToOne
    @JoinColumn(name = "coupon_id")  // Define the appropriate column name
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "menuItem_id")
    private MenuItem menuItem;

    @ManyToOne
    private Cart cart;





}
