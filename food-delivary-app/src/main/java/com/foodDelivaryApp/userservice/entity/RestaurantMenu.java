package com.foodDelivaryApp.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RestaurantMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String cuisineType;

    @OneToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;


    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , orphanRemoval = true)
    private List<MenuItem> items;


}
