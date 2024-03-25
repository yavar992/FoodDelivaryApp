package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.entity.Offer;

public interface OfferService {
    String createOffer(Offer offer , Long id);

    Object getOfferById(Long id);

    Object getAllOffersByRestaurantId(Long id);

    Object deleteOffers(Long id, Long restaurantId);
}
