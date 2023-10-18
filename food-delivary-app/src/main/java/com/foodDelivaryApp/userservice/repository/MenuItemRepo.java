package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuItemRepo extends JpaRepository<MenuItem , Long> {

    @Query(value = "SELECT * FROM `menuitem` WHERE name=?1 AND menu_id=?2",nativeQuery = true)
    MenuItem findByNameAndId(String name, Long menuId);
}
