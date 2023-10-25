package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.MenuItemDTO;
import com.foodDelivaryApp.userservice.entity.MenuItem;

import java.util.List;

public interface MenuItemService {

    String addItemToMenuList(Long ownerId, String uniqueIdentifierNumber, String foodCategoryCode, MenuItemDTO menuItemDTO);

    List<MenuItemDTO> findAllMenuItems(Long ownerId, String uniqueIdentifierNumber, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    List<MenuItemDTO> getAllMenuItems();

    String updateMenuItem(Long ownerId, String uniqueIdentifierNumber, String foodCode, MenuItemDTO menuItemDTO);

    String deleteMenuItem(Long ownerId, String uniqueIdentifierNumber, String foodCode);

    MenuItemDTO findMenuItemById(Long ownerId, String uniqueIdentifierNumber, Long id);

    List<MenuItemDTO> findAllMenuItemsByRestaurantId(Long ownerId, Long id, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    List<MenuItemDTO> findByCuisineTypes(Long ownerId, String uniqueIdentifierNumber, String cuisineType, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    List<MenuItemDTO> findAllMenuItemBetweenRanges(Long ownerId, String uniqueIdentifierNumber, Double startingPrice, Double endingPrice, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    MenuItemDTO getMenuItemByFoodCode(Long ownerId, String uniqueIdentifierNumber, String foodCode);

    MenuItemDTO getMenuItemByFoodName(Long ownerId, String uniqueIdentifierNumber, String foodName);

    List<MenuItemDTO> getMenuItemByPopularity(Long ownerId, String uniqueIdentifierNumber, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
}
