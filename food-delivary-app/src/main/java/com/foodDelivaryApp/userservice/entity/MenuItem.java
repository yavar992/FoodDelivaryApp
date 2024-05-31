package com.foodDelivaryApp.userservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MenuItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String foodCode;
    @Lob
    private List<Byte[]> images;
    private double popularity;
    private boolean availability;
    private LocalDateTime addedTime;
    private LocalDateTime updatedTime;
    private int sellCount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private RestaurantMenu menu;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ReviewAndRating> reviewAndRatings;

    @JsonIgnore
    @ManyToMany(mappedBy = "items")
    private List<Wishlist> wishlists;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_confirmation_id")
    private OrderConfirmationDetails orderConfirmationDetails;

}
