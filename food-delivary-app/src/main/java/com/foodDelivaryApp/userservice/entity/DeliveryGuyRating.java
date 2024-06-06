package com.foodDelivaryApp.userservice.entity;

import com.foodDelivaryApp.userservice.Enums.RatingEnum;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Entity
public class DeliveryGuyRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    @Enumerated(EnumType.ORDINAL)
    private RatingEnum rating;
    private String feedback;


    @ManyToOne
    @JoinColumn(name = "delivery_guy_id", insertable = false, updatable = false)
    private DeliveryGuy deliveryGuy;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;



}
