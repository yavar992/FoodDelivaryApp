package com.foodDelivaryApp.userservice.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {

    private String name;
    private String description;
    private Double price;

}
