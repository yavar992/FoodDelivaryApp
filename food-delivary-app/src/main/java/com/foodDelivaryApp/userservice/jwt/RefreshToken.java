package com.foodDelivaryApp.userservice.jwt;

import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "restaurantOwner_id" , referencedColumnName = "id")
    private RestaurantOwner restaurantOwner;

    @OneToOne
    @JoinColumn(name = "deliveryGuy_id" , referencedColumnName = "id")
    private DeliveryGuy deliveryGuy;

}
