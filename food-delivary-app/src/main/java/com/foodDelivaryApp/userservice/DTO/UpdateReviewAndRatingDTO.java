package com.foodDelivaryApp.userservice.DTO;

import com.foodDelivaryApp.userservice.entity.RatingEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@Builder
public class UpdateReviewAndRatingDTO {

    private String review;
    private RatingEnum rating;
}
