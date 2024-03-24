package com.foodDelivaryApp.userservice.service;

import java.util.List;

import com.foodDelivaryApp.userservice.dto.ReviewAndRatingDTO;
import com.foodDelivaryApp.userservice.dto.UpdateReviewAndRatingDTO;

public interface IReviewAndRatingService {
    String rateAndReviewMenuItem(Long menuId, ReviewAndRatingDTO reviewAndRatingDTO) throws IllegalAccessException;

    List<ReviewAndRatingDTO> getAllReviewAndRatingForMenuItems(Long menuId);

    List<ReviewAndRatingDTO> allReviews(Long menuId);

    String updateReviewAndRatings(Long reviewId, UpdateReviewAndRatingDTO updateReviewAndRatingDTO);

    String deleteReview(Long reviewId);
}
