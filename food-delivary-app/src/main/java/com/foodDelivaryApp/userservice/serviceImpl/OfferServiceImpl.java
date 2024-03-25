package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.entity.Offer;
import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.exceptionHandling.OfferException;
import com.foodDelivaryApp.userservice.exceptionHandling.RestaurantMenuException;
import com.foodDelivaryApp.userservice.repository.OfferRepo;
import com.foodDelivaryApp.userservice.repository.RestaurantRepo;
import com.foodDelivaryApp.userservice.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepo offerRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Override
    public String createOffer(Offer offer , Long id) {
        Restaurant restaurant = restaurantRepo.findById(id).orElseThrow(()-> new RestaurantMenuException(" No such restaurant found for id  " + id));
        offer.setRestaurant(restaurant);
         offerRepo.saveAndFlush(offer);
         return "offer created successfully !";
    }

    @Override
    public Object getOfferById(Long id) {
        return offerRepo.findById(id).orElseThrow(()-> new OfferException("No offer found for id  " + id));
    }

    @Override
    public Object getAllOffersByRestaurantId(Long id) {
        Restaurant restaurant = restaurantRepo.findById(id).orElseThrow(()-> new RestaurantMenuException(" No such restaurant found for id  " + id));
        List<Offer> offers = offerRepo.findAllOfferByRestaurantId(id);
        if (offers.isEmpty()){
            throw new OfferException("No offer found for restaurant " + restaurant.getName());
        }
        return offers;
    }

    @Override
    public Object deleteOffers(Long id, Long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(()-> new RestaurantMenuException(" No such restaurant found for id  " + id));
        Offer offer = offerRepo.findById(id).orElseThrow(()-> new OfferException("No offer found for id  " + id));
         offerRepo.delete(offer);
         return "offer deleted successfully !";

    }
}
