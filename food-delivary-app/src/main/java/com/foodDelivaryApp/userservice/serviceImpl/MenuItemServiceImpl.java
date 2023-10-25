package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.DTO.MenuItemDTO;
import com.foodDelivaryApp.userservice.convertor.MenuItemConvertor;
import com.foodDelivaryApp.userservice.entity.MenuItem;
import com.foodDelivaryApp.userservice.entity.Restaurant;
import com.foodDelivaryApp.userservice.entity.RestaurantMenu;
import com.foodDelivaryApp.userservice.exceptionHandling.InvalidRestaurantException;
import com.foodDelivaryApp.userservice.exceptionHandling.MenuItemException;
import com.foodDelivaryApp.userservice.exceptionHandling.RestaurantMenuException;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealCommon;
import com.foodDelivaryApp.userservice.repository.MenuItemRepo;
import com.foodDelivaryApp.userservice.repository.RestaurantMenuRepo;
import com.foodDelivaryApp.userservice.repository.RestaurantRepo;
import com.foodDelivaryApp.userservice.service.MenuItemService;
import com.foodDelivaryApp.userservice.service.RestaurantOwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @Autowired
    private RestaurantRepo restaurantRepo;


    @Autowired
    private RestaurantMenuRepo restaurantMenuRepo;

    @Autowired
    private HappyMealCommon happyMealCommon;



    @Override
    public String addItemToMenuList(Long ownerId, String uniqueIdentifierNumber, String foodCategoryCode, MenuItemDTO menuItemDTO) {
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
        MenuItem menuItem = MenuItemConvertor.convertMenuItemDTOToMenuItem(menuItemDTO);
        menuItem.setMenu(restaurantMenu1);
        if (isFoodExistByName(menuItem.getName() , menuItem.getMenu().getId())){
            throw new MenuItemException(menuItem.getName() + " is already register to the  " + restaurantMenu1.getName() + " Category");
        }
        menuItemRepo.saveAndFlush(menuItem);
        return "Item added successfully to the " + restaurantMenu1.getName();
    }

    @Override
    public List<MenuItemDTO> findAllMenuItems(Long ownerId, String uniqueIdentifierNumber, Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        restaurantOwnerService.findById(ownerId);
        happyMealCommon.findRestaurant(uniqueIdentifierNumber);
        Sort sort = Sort.by(sortBy);
        if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }else {
            sort = sort.ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize, sort);
        Page<MenuItem> menuItemPage = menuItemRepo.findAll(pageRequest);
        List<MenuItem> menuItems =  menuItemPage.getContent();
        return menuItems.stream().map(MenuItemConvertor::convertMenuItemToMenuItemDTO).collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemRepo.findAll();
        return menuItems.stream().map(MenuItemConvertor::convertMenuItemToMenuItemDTO).collect(Collectors.toList());
    }

    @Override
    public String updateMenuItem(Long ownerId, String uniqueIdentifierNumber, String foodCode, MenuItemDTO menuItemDTO) {
        restaurantOwnerService.findById(ownerId);
        happyMealCommon.findRestaurant(uniqueIdentifierNumber);
        MenuItem menuItem = happyMealCommon.findMenuItemByFoodCode(foodCode);
        MenuItemConvertor.updateMenuItem(menuItemDTO,menuItem);
        menuItemRepo.saveAndFlush(menuItem);
        return "MenuItem updated successfully !!";


    }

    @Override
    public String deleteMenuItem(Long ownerId, String uniqueIdentifierNumber, String foodCode) {
        restaurantOwnerService.findById(ownerId);
        happyMealCommon.findRestaurant(uniqueIdentifierNumber);
        MenuItem menuItem = happyMealCommon.findMenuItemByFoodCode(foodCode);
        menuItemRepo.delete(menuItem);
        return "MenuItem deleted successfully !!";
    }

    @Override
    public MenuItemDTO findMenuItemById(Long ownerId, String uniqueIdentifierNumber, Long id) {
        restaurantOwnerService.findById(ownerId);
        happyMealCommon.findRestaurant(uniqueIdentifierNumber);
        MenuItem menuItem = menuItemRepo.findById(id).orElseThrow(()->new MenuItemException("Could not find menuItem for the id " + id));
        return MenuItemConvertor.convertMenuItemToMenuItemDTO(menuItem);
    }

    @Override
    public List<MenuItemDTO> findAllMenuItemsByRestaurantId(Long ownerId,
                                                            Long id, Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        restaurantOwnerService.findById(ownerId);
        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(()-> new  InvalidRestaurantException("No restaurant found for the id " + id));
        Sort sort = Sort.by(sortBy);
        if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }else {
            sort = sort.ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize, sort);
        Page<MenuItem> menuItemPage = menuItemRepo.findAllMenuItemByRestaurantId(id , pageRequest);
        if (menuItemPage.isEmpty()){
            throw new MenuItemException("No MenuItem found for the id in " + restaurant.getName() + "the restaurant" + id);
        }
        return menuItemPage.stream().map(MenuItemConvertor::convertMenuItemToMenuItemDTO).collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> findByCuisineTypes(Long ownerId, String uniqueIdentifierNumber, String cuisineType, Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        restaurantOwnerService.findById(ownerId);
        happyMealCommon.findRestaurant(uniqueIdentifierNumber);
        List<MenuItem> menuItems = restaurantMenuRepo.findByCuisineTypes(cuisineType);
        if (menuItems.isEmpty()){
            throw new MenuItemException("No menu items found for cuisine type " + cuisineType);
        }
        return menuItems.stream().map(MenuItemConvertor::convertMenuItemToMenuItemDTO).collect(Collectors.toList());
        
    }

    @Override
    public List<MenuItemDTO> findAllMenuItemBetweenRanges(Long ownerId, String uniqueIdentifierNumber, Double startingPrice, Double endingPrice, Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        restaurantOwnerService.findById(ownerId);
        happyMealCommon.findRestaurant(uniqueIdentifierNumber);
        Sort sort = Sort.by(sortBy);
        if (sortOrder!=null && sortOrder.equalsIgnoreCase("desc")){
            sort = sort.descending();
        }
        else{
            sort = sort.ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize,sort);
        Page<MenuItem> menuItemPage = menuItemRepo.findAllMenuItemBetweenRanges(pageRequest, startingPrice,endingPrice);
        if (menuItemPage.isEmpty()){
            throw new MenuItemException("No menu item found between this starting prices and ending prices");
        }
        return menuItemPage.stream().map(MenuItemConvertor::convertMenuItemToMenuItemDTO).collect(Collectors.toList());
    }

    @Override
    public MenuItemDTO getMenuItemByFoodCode(Long ownerId, String uniqueIdentifierNumber, String foodCode) {
        restaurantOwnerService.findById(ownerId);
        happyMealCommon.findRestaurant(uniqueIdentifierNumber);
        Optional<MenuItem> menuItem = menuItemRepo.findByFoodCode(foodCode);
        if (menuItem.isEmpty()){
            throw new MenuItemException("No menu item found  for food code " + foodCode);
        }
        MenuItem menuItem1 = menuItem.get();
        return MenuItemConvertor.convertMenuItemToMenuItemDTO(menuItem1);
    }

    @Override
    public MenuItemDTO getMenuItemByFoodName(Long ownerId, String uniqueIdentifierNumber, String foodName) {
        restaurantOwnerService.findById(ownerId);
        happyMealCommon.findRestaurant(uniqueIdentifierNumber);
        Optional<MenuItem> menuItem = menuItemRepo.findByName(foodName);
        if (menuItem.isEmpty()){
            throw new MenuItemException("No menu item found  for food code " + foodName);
        }
        MenuItem menuItem1 = menuItem.get();
        return MenuItemConvertor.convertMenuItemToMenuItemDTO(menuItem1);
    }

    @Override
    public List<MenuItemDTO> getMenuItemByPopularity(Long ownerId, String uniqueIdentifierNumber, Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        restaurantOwnerService.findById(ownerId);
        happyMealCommon.findRestaurant(uniqueIdentifierNumber);
        Sort sort = Sort.by(sortBy);
        if (sortOrder!=null && sortOrder.equalsIgnoreCase("desc")){
            sort = sort.descending();
        }
        else{
            sort = sort.ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize,sort);
        Page<MenuItem> menuItemPage = menuItemRepo.findAllMenuItemByPopularity(pageRequest);
        if (menuItemPage.isEmpty()){
            throw new MenuItemException("No menu item found ");
        }
        return menuItemPage.stream().map(MenuItemConvertor::convertMenuItemToMenuItemDTO).collect(Collectors.toList());
    }


    public boolean isFoodExistByName(String name , Long menuId){
        MenuItem menuItem = menuItemRepo.findByNameAndId(name,menuId);
        return menuItem!=null;
    }
}
