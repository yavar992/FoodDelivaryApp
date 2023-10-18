package com.foodDelivaryApp.userservice.DTO;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemDTO {

    private String name;
    private String description;
    private Double price;
    private boolean availability;
    private String foodCode;
}
