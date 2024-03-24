package com.foodDelivaryApp.userservice.controller.administration;

import com.foodDelivaryApp.userservice.dto.RestaurantMenuDTO;
import com.foodDelivaryApp.userservice.entity.RestaurantMenu;
import com.foodDelivaryApp.userservice.service.IRestaurantMenuServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants/menu")
public class RestaurantMenuController {

    @Autowired
    private IRestaurantMenuServices restaurantMenuServices;

    @PostMapping("/{ownerId}")
    public ResponseEntity<?> addRestaurantMenuToRestaurant(@PathVariable("ownerId") Long ownerId ,
                                                           @RequestBody RestaurantMenuDTO restaurantMenu ,
                                                           @RequestParam("uniqueIdentifierNumber") String uniqueIdentifierNumber){
        try {
            if(restaurantMenuServices.foodAlreadyExistByCategory(restaurantMenu.getName())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Menu with the name " + restaurantMenu.getName() + " already exist in server");
            }
            RestaurantMenu restaurantMenu1 = restaurantMenuServices.addMenuToRestaurant(ownerId , restaurantMenu , uniqueIdentifierNumber);
            if (restaurantMenu1!=null){
                return ResponseEntity.status(HttpStatus.OK).body(restaurantMenu1);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot add the menu to restaurant due to invalid request");
    }


    @GetMapping("/getMenuByName/{ownerId}")
    public ResponseEntity<?> getMenuByName(@PathVariable("ownerId") Long ownerId ,
                                           @RequestParam("uniqueIdentifierNumber") String uniqueIdentifierNumber ,
                                           @RequestParam("foodCategoryCode") String foodCategoryCode){
        try {
            RestaurantMenu restaurantMenus = restaurantMenuServices.findRestaurantByName(ownerId, uniqueIdentifierNumber , foodCategoryCode);
            if (restaurantMenus!=null){
                return ResponseEntity.status(HttpStatus.OK).body(restaurantMenus);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the menu due to invalid request");
    }


    @GetMapping("/getAllMenu/{ownerId}/{uniqueIdentifierNumber}")
    public ResponseEntity<?> getAllMenu(@PathVariable("ownerId") Long ownerId ,
                                        @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber ,
                                        @RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                        @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                        @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder){
        try {
            List<RestaurantMenu> restaurantMenus = restaurantMenuServices
                    .findAllRestaurantMenuInPaginationMode(ownerId,uniqueIdentifierNumber,pageNo, pageSize , sortBy , sortOrder);
            if (!restaurantMenus.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(restaurantMenus);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot get the result due to invalid request");
    }

    @PostMapping("/{ownerId}/updateMenu/{uniqueIdentifierNumber}")
    public ResponseEntity<?> updateMenu(@PathVariable("ownerId") Long ownerId ,
                                        @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                        @RequestBody RestaurantMenuDTO restaurantMenuDTO ,
                                        @RequestParam("foodCategoryCode") String foodCategoryCode){
        try {
            RestaurantMenu restaurantMenu = restaurantMenuServices.updateMenu(ownerId,uniqueIdentifierNumber,restaurantMenuDTO,foodCategoryCode);
            if (restaurantMenu != null) {
                return ResponseEntity.status(HttpStatus.OK).body(restaurantMenuDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot update the menu due to invalid request");
    }

    @DeleteMapping("/{ownerId}/deleteMenu/{uniqueIdentifierNumber}")
    public ResponseEntity<?> deleteMenu(@PathVariable("ownerId") Long ownerId ,
                                        @PathVariable("uniqueIdentifierNumber") String uniqueIdentifierNumber,
                                        @RequestParam("foodCategoryCode") String foodCategoryCode){
        try {
            String deletedMessage = restaurantMenuServices.deleteMenu(ownerId,uniqueIdentifierNumber,foodCategoryCode);
            if (deletedMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(deletedMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot delete the menu due to invalid request");
    }

}
