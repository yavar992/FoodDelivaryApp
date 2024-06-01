package com.foodDelivaryApp.userservice.DTO;

import com.foodDelivaryApp.userservice.Enums.RatingEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UpdateReviewAndRatingDTO {

    private String review;
    private RatingEnum rating;
}
