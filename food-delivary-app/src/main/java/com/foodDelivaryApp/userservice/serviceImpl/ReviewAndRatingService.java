package com.foodDelivaryApp.userservice.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodDelivaryApp.userservice.convertor.RatingAndReviewConvertor;
import com.foodDelivaryApp.userservice.dto.ReviewAndRatingDTO;
import com.foodDelivaryApp.userservice.dto.UpdateReviewAndRatingDTO;
import com.foodDelivaryApp.userservice.entity.MenuItem;
import com.foodDelivaryApp.userservice.entity.ReviewAndRating;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.exceptionHandling.InvalidUserException;
import com.foodDelivaryApp.userservice.exceptionHandling.MenuItemException;
import com.foodDelivaryApp.userservice.exceptionHandling.ReviewAndRatingException;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealCommon;
import com.foodDelivaryApp.userservice.repository.MenuItemRepo;
import com.foodDelivaryApp.userservice.repository.ReviewAndRatingRepo;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.IReviewAndRatingService;

@Service
@Transactional
public class ReviewAndRatingService implements IReviewAndRatingService {

    @Autowired
    private ReviewAndRatingRepo reviewAndRatingRepo;

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HappyMealCommon happyMealCommon;

    @Override
    public String rateAndReviewMenuItem(Long menuId, ReviewAndRatingDTO reviewAndRatingDTO)  {
        MenuItem menuItem = menuItemRepo.findById(menuId).orElseThrow(()-> new MenuItemException("No menu Item found for the id " + menuId));

        ReviewAndRating reviewAndRating = RatingAndReviewConvertor.convertRatingAndReviewDtoToReviewAndRating(reviewAndRatingDTO);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepo.findByEmail(userDetails.getUsername());
        reviewAndRating.setMenuItem(menuItem);
        reviewAndRating.setUser(user);
        reviewAndRating.setUserName(user.getUsername());
        reviewAndRatingRepo.saveAndFlush(reviewAndRating);
        return "Rating added successfully !!";
    }

    @Override
    public List<ReviewAndRatingDTO> getAllReviewAndRatingForMenuItems(Long menuId) {
        happyMealCommon.findMealItemById(menuId);
        List<ReviewAndRating> reviewAndRatings = reviewAndRatingRepo.findByMenuItemId(menuId);
        if (reviewAndRatings.isEmpty()){
            throw new ReviewAndRatingException("no review found for the menuItem with id " + menuId);
        }
        return reviewAndRatings.stream().map(RatingAndReviewConvertor::convertReviewAndRatingToReviewAndReviewDTO).collect(Collectors.toList());

    }

    @Override
    public List<ReviewAndRatingDTO> allReviews(Long menuId) {
        List<ReviewAndRating> reviewAndRatings = reviewAndRatingRepo.findAll();
        if (reviewAndRatings.isEmpty()){
            throw new ReviewAndRatingException("no review found for the menuItem with id " + menuId);
        }
        return reviewAndRatings.stream().map(RatingAndReviewConvertor::convertReviewAndRatingToReviewAndReviewDTO).collect(Collectors.toList());
    }

    @Override
    public String updateReviewAndRatings(Long reviewId, UpdateReviewAndRatingDTO updateReviewAndRatingDTO) {
       ReviewAndRating rating = reviewAndRatingRepo.findById(reviewId).orElseThrow(()-> new ReviewAndRatingException("No review found for the reviewId " + reviewId) );
       User user = rating.getUser();
       long userId1= user.getId();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user1 = userRepo.findByEmail(userDetails.getUsername());
        Long loginUserId = user1.getId();
        if (!loginUserId.equals(userId1)){
            throw new InvalidUserException("please login first in order to update your review and rating");
        }
        rating.setRating(updateReviewAndRatingDTO.getRating());
        rating.setReview(updateReviewAndRatingDTO.getReview());
        rating.setUpdatedAt(LocalDateTime.now());
        reviewAndRatingRepo.saveAndFlush(rating);
        return "Review updated successfully";

    }

    @Override
    public String deleteReview(Long reviewId) {
        ReviewAndRating rating = reviewAndRatingRepo.findById(reviewId).orElseThrow(()-> new ReviewAndRatingException("No review found for the reviewId " + reviewId) );
        User user = rating.getUser();
        long userId1= user.getId();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user1 = userRepo.findByEmail(userDetails.getUsername());
        Long loginUserId = user1.getId();
        if (!loginUserId.equals(userId1)){
            throw new InvalidUserException("please login first in order to update your review and rating");
        }
        reviewAndRatingRepo.delete(rating);
        return "Review deleted successfully !";

    }
}
