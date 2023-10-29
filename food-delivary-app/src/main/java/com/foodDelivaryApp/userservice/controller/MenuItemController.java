package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.MenuItemDTO;
import com.foodDelivaryApp.userservice.service.MenuItemService;
import com.foodDelivaryApp.userservice.util.LocalDateTypeAdaptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping({"/{ownerId}/allMenuItem/{uniqueIdentifierNumber}","/allMenuItem"})
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

    @PostMapping("/{ownerId}/updateMenuItem/{uniqueIdentifierNumber}")
    public ResponseEntity<?> updateMenuItem(@PathVariable("ownerId") Long ownerId,
                                            @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                            @RequestParam("foodCode") String foodCode,
                                            @RequestBody MenuItemDTO menuItemDTO){
        try {
            String updateMessage = menuItemService.updateMenuItem(ownerId,uniqueIdentifierNumber,foodCode,menuItemDTO);
            if (updateMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(updateMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot update the menuItem due to bad request");
    }

    public ResponseEntity<?> deleteMenuItem(@PathVariable("ownerId") Long ownerId,
                                            @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                            @RequestParam("foodCode") String foodCode){
        try {
            String deleteMessage = menuItemService.deleteMenuItem(ownerId,uniqueIdentifierNumber,foodCode);
            if (deleteMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(deleteMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot delete the menuItem due to invalid request !!");
    }

    @GetMapping({"/{ownerId}/menuItem/{uniqueIdentifierNumber}/{id}","/menuItem"})
    public ResponseEntity<?> getMenuItemById(@PathVariable("ownerId") Long ownerId,
                                             @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                             @PathVariable("id") Long id){
        try {
            MenuItemDTO menuItemDTO = menuItemService.findMenuItemById(ownerId,uniqueIdentifierNumber,id);
            if (menuItemDTO!=null){
                return ResponseEntity.status(HttpStatus.OK).body(menuItemDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the menuItem by Id due to invalid request !!");
    }


    @GetMapping({"/{ownerId}/menuItemByRestaurantId/{id}","/menuItemByRestaurantId"})
    public ResponseEntity<?> getAllMenuItemByRestaurantId(@PathVariable("ownerId") Long ownerId,
                                                          @PathVariable("id") Long id,
                                                          @RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                          @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder)
     {
        try {
            List<MenuItemDTO> menuItemDTOS = menuItemService.findAllMenuItemsByRestaurantId(ownerId,id , pageNo , pageSize , sortBy , sortOrder);
            if (!menuItemDTOS.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(menuItemDTOS);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the menuItem due to invalid request");
    }

    @PostMapping({"{ownerId}/menuItemByCuisineType/{uniqueIdentifierNumber}/{cuisineType}","/menuItemByCuisineType"})
    public ResponseEntity<?> getAllMenuItemByCuisineType(@PathVariable("ownerId") Long ownerId,
                                                         @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                                         @PathVariable("cuisineType") String cuisineType,
                                                         @RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                                                         @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                         @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                         @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder){
        try {
            List<MenuItemDTO> menuItemDTOS = menuItemService.findByCuisineTypes(ownerId,uniqueIdentifierNumber,cuisineType,pageNo,pageSize,sortBy,sortOrder);
            if (!menuItemDTOS.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(menuItemDTOS);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the menuItem by the cuisineTypes !!");
    }


    @GetMapping({"{ownerId}/menuItemInRanges/{uniqueIdentifierNumber}","/menuItemInRanges"})
    public ResponseEntity<?> findAllMenuItemBetweenPricesRanges(@PathVariable("ownerId") Long ownerId,
                                                                @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                                                @RequestParam(value = "startingPrice") Double startingPrice,
                                                                @RequestParam(value = "endingPrice") Double endingPrice ,
                                                                @RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                                                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                                @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder){
        try {
            List<MenuItemDTO> menuItems = menuItemService.findAllMenuItemBetweenRanges(ownerId,uniqueIdentifierNumber,startingPrice,endingPrice,pageNo,pageSize,sortBy,sortOrder);
            if (!menuItems.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(menuItems);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the menuItem due to invalid request");
    }

    @PreAuthorize("hasAuthority('USER','RESTAURANTS_OWNER')")
    @GetMapping({"/{ownerId}/menuItemByFoodCode/{uniqueIdentifierNumber}","menuItemByFoodCode"})
    public ResponseEntity<?> getMenuItemByFoodCode(@PathVariable("ownerId") Long ownerId,
                                                   @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                                   @RequestParam(value = "foodCode") String foodCode){
        try {
            MenuItemDTO menuItemDTO = menuItemService.getMenuItemByFoodCode(ownerId,uniqueIdentifierNumber,foodCode);
            if (menuItemDTO!=null){
                return ResponseEntity.status(HttpStatus.OK).body(menuItemDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the menuItem due to invalid request");
    }

    @PreAuthorize("hasAuthority('USER','RESTAURANTS_OWNER')")
    @GetMapping({"/{ownerId}/menuItemByFoodName/{uniqueIdentifierNumber}","/menuItemByFoodName"})
    public ResponseEntity<?> getMenuItemByFoodName(@PathVariable("ownerId") Long ownerId,
                                                   @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                                   @RequestParam(value = "foodName") String foodName){
        try {
            MenuItemDTO menuItemDTO = menuItemService.getMenuItemByFoodName(ownerId,uniqueIdentifierNumber,foodName);
            if (menuItemDTO!=null){
                return ResponseEntity.status(HttpStatus.OK).body(menuItemDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the menuItem due to invalid request");
    }

    @PreAuthorize("hasAuthority('USER','RESTAURANTS_OWNER')")
    @GetMapping({"/{ownerId}/popularMenuItems/{uniqueIdentifierNumber}","/popularMenuItem"})
    public ResponseEntity<?> getAllPopularMenuItems(@PathVariable("ownerId") Long ownerId,
                                                    @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                                    @RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                    @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder){
        try {
            List<MenuItemDTO> menuItemDTOS = menuItemService.getMenuItemByPopularity(ownerId,uniqueIdentifierNumber,pageNo,pageSize,sortBy,sortOrder);
            if (!menuItemDTOS.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(menuItemDTOS);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the menuItems due to invalid request !!");
    }


    @GetMapping("/getAll")
    public ResponseEntity<?> getAllMenuItems(){
        List<MenuItemDTO> menuItems = menuItemService.getAllMenuItems();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class , new LocalDateTypeAdaptor()).create();
        String json = gson.toJson(menuItems);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

}
