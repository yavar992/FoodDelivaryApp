package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.ReviewAndRatingDTO;
import com.foodDelivaryApp.userservice.DTO.UpdateReviewAndRatingDTO;

import java.util.List;

public interface ReviewAndRatingService {
    String rateAndReviewMenuItem(Long menuId, ReviewAndRatingDTO reviewAndRatingDTO) throws IllegalAccessException;

    List<ReviewAndRatingDTO> getAllReviewAndRatingForMenuItems(Long menuId);

    List<ReviewAndRatingDTO> allReviews(Long menuId);

    String updateReviewAndRatings(Long reviewId, UpdateReviewAndRatingDTO updateReviewAndRatingDTO);

    String deleteReview(Long reviewId);
}
