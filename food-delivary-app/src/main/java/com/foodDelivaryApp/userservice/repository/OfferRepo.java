package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepo extends JpaRepository<Offer,Long> {

    @Query(value = "SELECT * FROM `offer` WHERE restaurant_id =?1" , nativeQuery = true )
    List<Offer> findAllOfferByRestaurantId(Long id);

}
