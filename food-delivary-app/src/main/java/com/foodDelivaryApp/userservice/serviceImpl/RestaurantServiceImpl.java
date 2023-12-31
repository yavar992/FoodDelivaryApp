package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.DTO.RestaurantDTO;
import com.foodDelivaryApp.userservice.convertor.RestaurantConvertor;
import com.foodDelivaryApp.userservice.entity.CuisineType;
import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.event.RestaurantEvent;
import com.foodDelivaryApp.userservice.exceptionHandling.InvalidOTPException;
import com.foodDelivaryApp.userservice.exceptionHandling.InvalidRestaurantException;
import com.foodDelivaryApp.userservice.repository.RestaurantRepo;
import com.foodDelivaryApp.userservice.service.RestaurantOwnerService;
import com.foodDelivaryApp.userservice.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantsService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public String addRestaurant(Long ownerId, RestaurantDTO restaurantDTO) {
        RestaurantOwner restaurantOwner = restaurantOwnerService.findById(ownerId);
        Restaurant restaurant = RestaurantConvertor.convertRestaurantDTOToRestaurant(restaurantDTO);
        RestaurantEvent restaurantEvent = new RestaurantEvent(restaurant);
        applicationEventPublisher.publishEvent(restaurantEvent);
        restaurant.setRestaurantOwner(restaurantOwner);
        restaurantRepo.saveAndFlush(restaurant);
        return restaurantOwner.getFirstName() + " " + restaurantOwner.getLastName() + " SUCCESSFULLY ADDED THE " + restaurant.getName() + " RESTAURANT TO THE HAPPY-MEAL FOOD DELIVERY APP";
    }

    @Override
    public List<RestaurantDTO> findAllRestaurant() {
        List<Restaurant> restaurants = restaurantRepo.findAll();
        if (restaurants.isEmpty()){
            throw new InvalidOTPException("No restaurant found ");
        }
        return restaurants.stream().map(RestaurantConvertor::convertRestaurantToRestaurantDTO).collect(Collectors.toList());
    }

    @Override
    public String deleteRestaurant(Long ownerId, String uniqueIdentifierNumber) {
         restaurantOwnerService.findById(ownerId);
        Optional<Restaurant> restaurant = restaurantRepo.findByUniqueIdentifierNumber(uniqueIdentifierNumber);
        if (restaurant.isEmpty()){
            throw new InvalidRestaurantException("No restaurant found for the uniqueIdentifierNumber " + uniqueIdentifierNumber);
        }
        Restaurant restaurant1 = restaurant.get();
        restaurantRepo.delete(restaurant1);
        return "Restaurant Deleted successfully !";
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantDTO getRestaurantByUniqueIdentifierNumber(Long ownerId ,String uniqueIdentifierNumber) {
        restaurantOwnerService.findById(ownerId);
        Optional<Restaurant> restaurant = restaurantRepo.findByUniqueIdentifierNumber(uniqueIdentifierNumber);
        if (restaurant.isEmpty()){
            throw new InvalidRestaurantException("No restaurant found for the uniqueIdentifierNumber " + uniqueIdentifierNumber);
        }
        Restaurant restaurant1 = restaurant.get();
        return RestaurantConvertor.convertRestaurantToRestaurantDTO(restaurant1);
    }

    @Override
    public String updateRestaurant(Long ownerId, String uniqueIdentifierNumber , RestaurantDTO restaurantDTO) {
        restaurantOwnerService.findById(ownerId);
        Optional<Restaurant> restaurant = restaurantRepo.findByUniqueIdentifierNumber(uniqueIdentifierNumber);
        if (restaurant.isEmpty()){
            throw new InvalidRestaurantException("No restaurant found for the uniqueIdentifierNumber " + uniqueIdentifierNumber);
        }
        Restaurant restaurant1 = restaurant.get();
        RestaurantConvertor.updateRestaurant(restaurantDTO , restaurant1);
        restaurantRepo.saveAndFlush(restaurant1);
        return "Restaurant Updated SuccessFully !";
    }

    @Override
    public List<CuisineType> findAllCuisineTypes(Long ownerId, String uniqueIdentifierNumber) {
        restaurantOwnerService.findById(ownerId);
        Optional<Restaurant> restaurant = restaurantRepo.findByUniqueIdentifierNumber(uniqueIdentifierNumber);
        if (restaurant.isEmpty()){
            throw new InvalidRestaurantException("No restaurant found for the uniqueIdentifierNumber " + uniqueIdentifierNumber);
        }
        Restaurant restaurant1 = restaurant.get();
        return restaurant1.getCuisineType();
    }
}
