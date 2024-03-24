package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.dto.CouponDTO;
import com.foodDelivaryApp.userservice.dto.CouponResponseDTO;
import com.foodDelivaryApp.userservice.entity.Coupon;

import java.time.LocalDate;
import java.util.List;

public interface ICouponService {

    Coupon createCoupon(CouponDTO coupon , String name);

    String extendCouponDuration(Long id, LocalDate ExpirationPeriod);

    String updateCoupon(Long id, CouponDTO couponDTO , String email) ;

    String deleteCoupon(Long id);

    CouponResponseDTO couponById(Long id);

    List<CouponResponseDTO> allCoupons();
}
