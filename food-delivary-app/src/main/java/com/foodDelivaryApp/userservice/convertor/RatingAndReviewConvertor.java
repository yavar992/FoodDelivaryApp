package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.dto.ReviewAndRatingDTO;
import com.foodDelivaryApp.userservice.entity.ReviewAndRating;

import java.time.LocalDateTime;

public interface RatingAndReviewConvertor {

    static ReviewAndRating convertRatingAndReviewDtoToReviewAndRating(ReviewAndRatingDTO reviewAndRatingDTO){

        return ReviewAndRating.builder()
                .customerEmail(reviewAndRatingDTO.getCustomerEmail())
                .customerName(reviewAndRatingDTO.getCustomerName())
                .rating(reviewAndRatingDTO.getRating())
                .review(reviewAndRatingDTO.getReview())
                .createdAt(LocalDateTime.now())
                .build();
    }

    static ReviewAndRatingDTO convertReviewAndRatingToReviewAndReviewDTO(ReviewAndRating reviewAndRating){
        return ReviewAndRatingDTO.builder()
                .customerEmail(reviewAndRating.getCustomerEmail())
                .customerName(reviewAndRating.getCustomerName())
                .rating(reviewAndRating.getRating())
                .review(reviewAndRating.getReview())
                .build();
    }

}
