package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.CouponDTO;
import com.foodDelivaryApp.userservice.DTO.CouponResponseDTO;
import com.foodDelivaryApp.userservice.entity.Coupon;

import java.time.LocalDate;
import java.util.List;

public interface CouponService {

    Coupon createCoupon(CouponDTO coupon);

    String extendCouponDuration(Long id, LocalDate ExpirationPeriod);

    String updateCoupon(Long id, CouponDTO couponDTO);

    String deleteCoupon(Long id);

    CouponResponseDTO couponById(Long id);

    List<CouponResponseDTO> allCoupons();
}
