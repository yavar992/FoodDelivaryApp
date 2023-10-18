package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.DTO.MenuItemDTO;
import com.foodDelivaryApp.userservice.entity.MenuItem;
import com.foodDelivaryApp.userservice.util.OTPUtil;

import java.time.LocalDateTime;

public interface MenuItemConvertor {

    static MenuItem convertMenuItemDTOToMenuItem(MenuItemDTO menuItemDTO){
        return MenuItem.builder()
                .name(menuItemDTO.getName())
                .addedTime(LocalDateTime.now())
                .availability(menuItemDTO.isAvailability())
                .description(menuItemDTO.getDescription())
                .price(menuItemDTO.getPrice())
                .foodCode(menuItemDTO.getName().substring(0,2)+ OTPUtil.random3Digit())
                .build();
    }

    static MenuItemDTO convertMenuItemToMenuItemDTO(MenuItem menuItem){
        return MenuItemDTO.builder()
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .description(menuItem.getDescription())
                .availability(menuItem.isAvailability())
                .foodCode(menuItem.getFoodCode())
                .build();
    }
}
