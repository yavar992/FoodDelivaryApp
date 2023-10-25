package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MenuItemRepo extends JpaRepository<MenuItem , Long> {

    @Query(value = "SELECT * FROM `menuitem` WHERE name=?1 AND menu_id=?2",nativeQuery = true)
    MenuItem findByNameAndId(String name, Long menuId);

    Optional<MenuItem> findByFoodCode(String foodCode);

    @Query(value = "SELECT mi.id , mi.name , mi.description , mi.price , mi.foodCode , mi.availability , mi.addedTime , mi.images , mi.popularity , mi.updatedTime , mi.menu_id , mi.sellCount FROM `menuitem` AS mi LEFT JOIN restaurantmenu ON restaurantmenu.id = mi.menu_id LEFT JOIN restaurant ON restaurant.id = restaurantmenu.restaurant_id WHERE restaurant.id = ?1",nativeQuery = true)
    Page<MenuItem> findAllMenuItemByRestaurantId(Long id , PageRequest pageRequest);

    @Query(value = "SELECT * FROM menuitem WHERE price BETWEEN ?1 AND ?2" , nativeQuery = true)
    Page<MenuItem> findAllMenuItemBetweenRanges(PageRequest pageRequest, Double startingPrice, Double endingPrice);


    Optional<MenuItem> findByName(String foodName);

    @Query(value = " SELECT * FROM menuitem WHERE price ORDER BY popularity DESC" , nativeQuery = true)
    Page<MenuItem> findAllMenuItemByPopularity(PageRequest pageRequest);
}
