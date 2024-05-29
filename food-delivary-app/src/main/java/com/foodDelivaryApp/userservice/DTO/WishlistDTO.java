package com.foodDelivaryApp.userservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodDelivaryApp.userservice.entity.MenuItem;
import com.foodDelivaryApp.userservice.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WishlistDTO {


    private Long id;
    private String name;
    private String description;
    private Double price;
    private String foodCode;
}
