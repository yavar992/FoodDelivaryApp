package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails , Long> {
}
