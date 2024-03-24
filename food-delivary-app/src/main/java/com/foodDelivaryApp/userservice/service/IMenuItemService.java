package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.dto.MenuItemDTO;
import com.foodDelivaryApp.userservice.entity.MenuItem;

import java.util.List;

public interface IMenuItemService {

    String addItemToMenuList(Long ownerId, String uniqueIdentifierNumber, String foodCategoryCode, MenuItemDTO menuItemDTO);

    List<MenuItemDTO> findAllMenuItems( Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    List<MenuItemDTO> getAllMenuItems();

    String updateMenuItem(Long ownerId, String uniqueIdentifierNumber, String foodCode, MenuItemDTO menuItemDTO);

    String deleteMenuItem(Long ownerId, String uniqueIdentifierNumber, String foodCode);

    MenuItemDTO findMenuItemById(Long id);

    List<MenuItemDTO> findAllMenuItemsByRestaurantId( Long id, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    List<MenuItemDTO> findByCuisineTypes(String cuisineType, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    List<MenuItemDTO> findAllMenuItemBetweenRanges( Double startingPrice, Double endingPrice, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    MenuItemDTO getMenuItemByFoodCode( String foodCode);

    MenuItemDTO getMenuItemByFoodName( String foodName);

    List<MenuItemDTO> getMenuItemByPopularity( Integer pageNo, Integer pageSize, String sortBy, String sortOrder);


    String checkout(Long itemId);

    MenuItem findById(Long itemId);
}
