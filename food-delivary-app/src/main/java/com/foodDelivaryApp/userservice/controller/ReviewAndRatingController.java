package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.dto.ReviewAndRatingDTO;
import com.foodDelivaryApp.userservice.dto.UpdateReviewAndRatingDTO;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealConstant;
import com.foodDelivaryApp.userservice.service.IReviewAndRatingService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/rateAndReview")
public class ReviewAndRatingController {

    @Autowired
    private IReviewAndRatingService reviewAndRatingService;


    @PostMapping("/{menuId}")
    public ResponseEntity<?> rateMenuItem(@PathVariable("menuId") Long menuId , @RequestBody ReviewAndRatingDTO reviewAndRatingDTO ){
        try {
            String reviewAndRatingMessaage = reviewAndRatingService.rateAndReviewMenuItem(menuId,reviewAndRatingDTO);
                if (reviewAndRatingMessaage!=null){
                    return ResponseEntity.status(HttpStatus.CREATED).body(reviewAndRatingDTO);
                }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }


    @GetMapping("/{menuId}/reviews")
    public ResponseEntity<?> getReviewAndRating(@PathVariable("menuId") Long menuId){
        try {
            List<ReviewAndRatingDTO> reviewAndRatingDTOList = reviewAndRatingService.getAllReviewAndRatingForMenuItems(menuId);
            if (!reviewAndRatingDTOList.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(reviewAndRatingDTOList);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }


    @GetMapping("/{menuId}/allReviews")
    public ResponseEntity<?> allReview(@PathVariable("menuId") Long menuId){
        try {
            List<ReviewAndRatingDTO> reviewAndRatingDTOList = reviewAndRatingService.allReviews(menuId);
            if (!reviewAndRatingDTOList.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(reviewAndRatingDTOList);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @PostMapping("/{reviewId}/updateReviews")
    public ResponseEntity<?> updateReviewAndRating(@PathVariable(value = "reviewId") Long reviewId, @RequestBody UpdateReviewAndRatingDTO updateReviewAndRatingDTO){
        try {
            String updateMessage = reviewAndRatingService.updateReviewAndRatings(reviewId,updateReviewAndRatingDTO);
            if (updateMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(updateMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @DeleteMapping("/{reviewId}/deleteReview")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") Long reviewId){
        try {
            String updateMessage = reviewAndRatingService.deleteReview(reviewId);
            if (updateMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(updateMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }



}
