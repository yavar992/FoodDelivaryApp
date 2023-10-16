package com.foodDelivaryApp.userservice.entity;


import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private RestaurantMenu menu;

}
