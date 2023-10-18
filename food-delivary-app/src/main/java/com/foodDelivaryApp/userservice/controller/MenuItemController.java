package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.MenuItemDTO;
import com.foodDelivaryApp.userservice.entity.MenuItem;
import com.foodDelivaryApp.userservice.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/restaurants/menu/Items")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/{ownerId}/{uniqueIdentifierNumber}")
    public ResponseEntity<?> addItemToMenu(@PathVariable("ownerId") Long ownerId,
                                           @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                           @RequestParam("foodCategoryCode") String foodCategoryCode,
                                           @RequestBody MenuItemDTO menuItemDTO){
        try {
            String addedMessage = menuItemService.addItemToMenuList(ownerId,uniqueIdentifierNumber,foodCategoryCode , menuItemDTO);
            if (addedMessage!=null){
                return ResponseEntity.status(HttpStatus.CREATED).body(addedMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot add the item to the menu due to invalid request ");
    }

    @GetMapping("/{ownerId}/allMenuItem/{uniqueIdentifierNumber}")
    public ResponseEntity<?> getAllMenuItem(@PathVariable("ownerId") Long ownerId ,
                                            @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                            @RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder){
        try {
            List<MenuItemDTO> menuItemDTOS = menuItemService.findAllMenuItems(ownerId,uniqueIdentifierNumber,pageNo,pageSize,sortBy,sortOrder);
            if (!menuItemDTOS.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(menuItemDTOS);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the menuItem due to invalid request");

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllMenuItems(){
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        return ResponseEntity.status(HttpStatus.OK).body(menuItems);
    }

}
