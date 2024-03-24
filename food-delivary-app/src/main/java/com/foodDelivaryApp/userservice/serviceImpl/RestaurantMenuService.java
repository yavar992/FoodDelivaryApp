package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.convertor.RestaurantMenuConvertor;
import com.foodDelivaryApp.userservice.dto.RestaurantMenuDTO;
import com.foodDelivaryApp.userservice.entity.CuisineType;
import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.entity.RestaurantMenu;
import com.foodDelivaryApp.userservice.exceptionHandling.InvalidRestaurantException;
import com.foodDelivaryApp.userservice.exceptionHandling.RestaurantMenuException;
import com.foodDelivaryApp.userservice.repository.RestaurantMenuRepo;
import com.foodDelivaryApp.userservice.repository.RestaurantRepository;
import com.foodDelivaryApp.userservice.service.IRestaurantMenuServices;
import com.foodDelivaryApp.userservice.service.IRestaurantOwnerService;
import com.foodDelivaryApp.userservice.service.IRestaurantsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class RestaurantMenuService implements IRestaurantMenuServices {

    @Autowired
    private RestaurantMenuRepo restaurantMenuRepo;

    @Autowired
    private IRestaurantOwnerService restaurantOwnerService;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private IRestaurantsService restaurantsService;


    @Override
    public RestaurantMenu addMenuToRestaurant(Long ownerId, RestaurantMenuDTO restaurantMenu, String uniqueIdentifierNumber) {
        restaurantOwnerService.findById(ownerId);
        Optional<Restaurant> restaurant = restaurantRepo.findByUniqueIdentifierNumber(uniqueIdentifierNumber);
        if (restaurant.isEmpty()){
            throw new InvalidRestaurantException("No restaurant found for the uniqueIdentifierNumber " + uniqueIdentifierNumber);
        }
        Restaurant restaurant1 = restaurant.get();
        log.info("restaurant {}",restaurant1);
        List<CuisineType> cuisineTypes = restaurantsService.findAllCuisineTypes(ownerId , uniqueIdentifierNumber);
        RestaurantMenu restaurantMenu1 = RestaurantMenuConvertor.convertRestaurantMenuDTToRestaurantMenu(restaurantMenu);
        log.info("restaurantMenu {}", restaurantMenu1);
        if (!cuisineTypes.contains(restaurantMenu1.getCuisineType())) {
            throw new InvalidRestaurantException("Please Enter the correct cuisine types  ");
        }
        restaurantMenu1.setRestaurant(restaurant1);
        return restaurantMenuRepo.saveAndFlush(restaurantMenu1);
    }

    @Override
    public boolean foodAlreadyExistByCategory(String name) {
        RestaurantMenu restaurantMenu = restaurantMenuRepo.findByName(name);
        return restaurantMenu!=null;
    }

    @Override
    public List<RestaurantMenu> findAAllRestaurntsMenu() {
        return restaurantMenuRepo.findAll();
    }

    @Override
    public RestaurantMenu findRestaurantByName(Long ownerId, String uniqueIdentifierNumber, String foodCategoryCode) {
        restaurantOwnerService.findById(ownerId);
        Optional<Restaurant> restaurant = restaurantRepo.findByUniqueIdentifierNumber(uniqueIdentifierNumber);
        if (restaurant.isEmpty()){
            throw new InvalidRestaurantException("No restaurant found for the uniqueIdentifierNumber " + uniqueIdentifierNumber);
        }
        Optional<RestaurantMenu> restaurantMenu = restaurantMenuRepo.findByFoodCategoryCode(foodCategoryCode);
        if (restaurantMenu.isEmpty()){
            throw new RestaurantMenuException("No food foodCategoryCode for the foodCategoryCode " + foodCategoryCode);
        }
        return restaurantMenu.get();
    }

    @Override
    public List<RestaurantMenu> findAllRestaurantMenuInPaginationMode(Long ownerId, String uniqueIdentifierNumber, Integer pageNo, Integer pageSize, String sortBy , String sortOrder) {
        restaurantOwnerService.findById(ownerId);
        Optional<Restaurant> restaurant = restaurantRepo.findByUniqueIdentifierNumber(uniqueIdentifierNumber);
        if (restaurant.isEmpty()){
            throw new InvalidRestaurantException("No restaurant found for the uniqueIdentifierNumber " + uniqueIdentifierNumber);
        }
        Sort sort = Sort.by(sortBy);
        if (sortOrder!=null && sortOrder.equalsIgnoreCase("desc")){
            sort = sort.descending();
        }
        else {
            sort = sort.ascending();
        }

        PageRequest pageRequest = PageRequest.of(pageNo,pageSize , sort);
        Page<RestaurantMenu> restaurantMenuPage = restaurantMenuRepo.findAll(pageRequest);
        if (restaurantMenuPage.isEmpty()){
            throw new RestaurantMenuException("No data found for the request in server");
        }
        return restaurantMenuPage.getContent();

    }

    @Override
    public RestaurantMenu updateMenu(Long ownerId, String uniqueIdentifierNumber, RestaurantMenuDTO restaurantMenuDTO, String foodCategoryCode) {
        restaurantOwnerService.findById(ownerId);
        Optional<Restaurant> restaurant = restaurantRepo.findByUniqueIdentifierNumber(uniqueIdentifierNumber);
        if (restaurant.isEmpty()){
            throw new InvalidRestaurantException("No restaurant found for the uniqueIdentifierNumber " + uniqueIdentifierNumber);
        }
        Optional<RestaurantMenu> restaurantMenu = restaurantMenuRepo.findByFoodCategoryCode(foodCategoryCode);
        if (restaurantMenu.isEmpty()){
            throw new RestaurantMenuException("No food foodCategoryCode for the foodCategoryCode " + foodCategoryCode);
        }
        RestaurantMenu restaurantMenu1 = restaurantMenu.get();
        RestaurantMenuConvertor.updateRestaurantMenu(restaurantMenuDTO,restaurantMenu1);
        return restaurantMenuRepo.saveAndFlush(restaurantMenu1);
    }

    @Override
    public String deleteMenu(Long ownerId, String uniqueIdentifierNumber, String foodCategoryCode) {
        restaurantOwnerService.findById(ownerId);
        Optional<Restaurant> restaurant = restaurantRepo.findByUniqueIdentifierNumber(uniqueIdentifierNumber);
        if (restaurant.isEmpty()){
            throw new InvalidRestaurantException("No restaurant found for the uniqueIdentifierNumber " + uniqueIdentifierNumber);
        }
        Optional<RestaurantMenu> restaurantMenu = restaurantMenuRepo.findByFoodCategoryCode(foodCategoryCode);
        if (restaurantMenu.isEmpty()){
            throw new RestaurantMenuException("No food foodCategoryCode for the foodCategoryCode " + foodCategoryCode);
        }
        RestaurantMenu restaurantMenu1 = restaurantMenu.get();
        restaurantMenuRepo.delete(restaurantMenu1);
        return "Menu deleted successfully !!";
    }


}
