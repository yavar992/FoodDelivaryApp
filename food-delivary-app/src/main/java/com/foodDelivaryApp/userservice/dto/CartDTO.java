package com.foodDelivaryApp.userservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {

    private String name;
    private String description;
    private Double price;
    private Long quantity;


}
