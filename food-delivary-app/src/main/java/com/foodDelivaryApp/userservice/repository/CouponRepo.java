package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepo extends JpaRepository<Coupon,Long> {
}
