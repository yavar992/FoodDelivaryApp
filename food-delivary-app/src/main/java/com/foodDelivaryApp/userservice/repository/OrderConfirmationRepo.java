package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.OrderConfirmationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderConfirmationRepo extends JpaRepository<OrderConfirmationDetails , Long> {
}
