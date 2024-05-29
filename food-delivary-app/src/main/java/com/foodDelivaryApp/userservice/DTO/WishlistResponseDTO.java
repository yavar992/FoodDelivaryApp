package com.foodDelivaryApp.userservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WishlistResponseDTO {

    private Long id;
    private WishlistDTO wishlistDTO;
}
