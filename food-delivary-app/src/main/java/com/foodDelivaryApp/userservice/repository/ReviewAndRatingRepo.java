package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.ReviewAndRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewAndRatingRepo extends JpaRepository<ReviewAndRating , Long> {

}
