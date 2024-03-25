package com.foodDelivaryApp.userservice.controller.administration;

import com.foodDelivaryApp.userservice.entity.Offer;
import com.foodDelivaryApp.userservice.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurant/offer")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @PostMapping("/id")
    public ResponseEntity<?> createOffer(@Valid @RequestBody Offer offer , Long id){
        String offerMsg = offerService.createOffer(offer , id);
        return ResponseEntity.status(HttpStatus.CREATED).body(offerMsg);
    }

    @GetMapping("/id")
    public ResponseEntity<?> checkOfferById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(offerService.getOfferById(id));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> checkAllOffers(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(offerService.getAllOffersByRestaurantId(id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteOffers(@PathVariable("id") Long id , @RequestParam("restaurantId") Long restaurantId){
        return ResponseEntity.status(HttpStatus.OK).body(offerService.deleteOffers(id, restaurantId));
    }



}
