package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.ReviewAndRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewAndRatingRepo extends JpaRepository<ReviewAndRating , Long> {

    @Query(value = "SELECT * FROM reviewandrating WHERE menuItem_id = ?1" , nativeQuery = true)
    List<ReviewAndRating> findByMenuItemId(Long menuId);

    @Query(value = "SELECT * FROM reviewandrating WHERE menuItem_id = ?1" , nativeQuery = true)
    ReviewAndRating findByMenuId(Long menuId);
}
