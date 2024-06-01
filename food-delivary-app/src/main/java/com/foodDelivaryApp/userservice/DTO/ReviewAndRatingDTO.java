package com.foodDelivaryApp.userservice.DTO;

import com.foodDelivaryApp.userservice.Enums.RatingEnum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReviewAndRatingDTO {



    private String customerName;
    private String customerEmail;
    private String review;
    @Enumerated(EnumType.ORDINAL)
    private RatingEnum rating;

    public RatingEnum getRating() {
        return rating;
    }



}
