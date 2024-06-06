package com.foodDelivaryApp.userservice.DTO;

import com.foodDelivaryApp.userservice.Enums.RatingEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class DeliveryGuyRatingDTO {

    private Long orderId;
    @Enumerated(EnumType.STRING)
    private RatingEnum rating;
    private String feedback;

}
