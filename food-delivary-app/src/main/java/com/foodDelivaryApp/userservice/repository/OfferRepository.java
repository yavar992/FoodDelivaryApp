package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer,Long> {
}
