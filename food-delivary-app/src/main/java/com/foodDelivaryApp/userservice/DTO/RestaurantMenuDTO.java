package com.foodDelivaryApp.userservice.DTO;

import com.foodDelivaryApp.userservice.entity.CuisineType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RestaurantMenuDTO {


    private String name;
    private CuisineType cuisineType;
}
