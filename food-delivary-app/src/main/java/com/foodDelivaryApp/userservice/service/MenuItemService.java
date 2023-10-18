package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.MenuItemDTO;
import com.foodDelivaryApp.userservice.entity.MenuItem;

import java.util.List;

public interface MenuItemService {

    String addItemToMenuList(Long ownerId, String uniqueIdentifierNumber, String foodCategoryCode, MenuItemDTO menuItemDTO);

    List<MenuItemDTO> findAllMenuItems(Long ownerId, String uniqueIdentifierNumber, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    List<MenuItem> getAllMenuItems();
}
