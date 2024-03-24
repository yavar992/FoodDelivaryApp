package com.foodDelivaryApp.userservice.dto;

import com.foodDelivaryApp.userservice.entity.RatingEnum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
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
