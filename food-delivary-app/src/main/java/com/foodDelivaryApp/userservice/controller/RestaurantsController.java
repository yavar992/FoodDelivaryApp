package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.RestaurantDTO;
import com.foodDelivaryApp.userservice.entity.CuisineType;
import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.service.RestaurantsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantsService restaurantsService;

    @PostMapping({"signup/{ownerId}","register/{ownerId}"})
    public ResponseEntity<?> addRestaurant(@PathVariable("ownerId") Long ownerId , @Valid @RequestBody RestaurantDTO restaurantDTO){
        try {
            String successMessage = restaurantsService.addRestaurant(ownerId , restaurantDTO);
            if (successMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(successMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("cannot added the restaurant due to invalid request");
    }

    @GetMapping("/allRestaurant")
    public ResponseEntity<?> findAllRestaurant(){
        try {
            List<RestaurantDTO> message = restaurantsService.findAllRestaurant();
            if (message!=null){
                return ResponseEntity.status(HttpStatus.OK).body(message);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot retrieve the list of the restaurant due to invalid request");
    }

    @DeleteMapping("/deleteRestaurant/{ownerId}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable("ownerId") Long ownerId ,
                                              @RequestParam("uniqueIdentifierNumber") String uniqueIdentifierNumber){
        try {
            String deleteMessage = restaurantsService.deleteRestaurant(ownerId,uniqueIdentifierNumber);
            if(deleteMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(deleteMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot delete the restaurant due to invalid request");
    }

    @GetMapping("/getRestaurant/{ownerId}")
    public ResponseEntity<?> getRestaurantByUniqueIdentifierNumber(@PathVariable("ownerId") Long ownerId,
                                                                   @RequestParam("uniqueIdentifierNumber") String uniqueIdentifierNumber){
        try {
            RestaurantDTO restaurantDTO = restaurantsService.getRestaurantByUniqueIdentifierNumber(ownerId , uniqueIdentifierNumber);
            if (restaurantDTO!=null){
                return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the restaurant by the uniqueIdentifierNumber due to invalid request");
    }

    @PostMapping("/updateRestaurant/{ownerId}")
    public ResponseEntity<?> updateRestaurant(@PathVariable("ownerId") Long ownerId ,
                                             @RequestParam("uniqueIdentifierNumber") String uniqueIdentifierNumber ,
                                              @RequestBody RestaurantDTO restaurantDTO){
        try {
            String updateMessage = restaurantsService.updateRestaurant(ownerId , uniqueIdentifierNumber , restaurantDTO);
            if (updateMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(updateMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot update the restaurant due to invalid request");
    }

    @GetMapping("/cuisineTypes/{ownerId}")
    public ResponseEntity<?> findAllCuisineTypes(@PathVariable("ownerId") Long ownerId ,
                                                 @RequestParam("uniqueIdentifierNumber") String uniqueIdentifierNumber){

        try {
            List<CuisineType> cuisineTypes = restaurantsService.findAllCuisineTypes(ownerId, uniqueIdentifierNumber);
            if (!cuisineTypes.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(cuisineTypes);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the cuisineTypes due to invalid request");
    }


}
