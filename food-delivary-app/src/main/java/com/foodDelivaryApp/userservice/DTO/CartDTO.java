package com.foodDelivaryApp.userservice.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class CartDTO {

    private String name;
    private String description;
    private Double price;

}
