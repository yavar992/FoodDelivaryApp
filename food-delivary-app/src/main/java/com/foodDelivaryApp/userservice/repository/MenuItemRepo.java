package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepo extends JpaRepository<MenuItem , Long> {
}
