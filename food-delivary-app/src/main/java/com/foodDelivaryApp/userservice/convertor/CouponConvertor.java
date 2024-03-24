package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.dto.CouponDTO;
import com.foodDelivaryApp.userservice.dto.CouponResponseDTO;
import com.foodDelivaryApp.userservice.entity.Coupon;
import com.foodDelivaryApp.userservice.util.GeneratedRandomNumber;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CouponConvertor {


    static Coupon convertCouponDTOToCoupon(CouponDTO couponDTO){
        return Coupon.builder()
                .couponStock(couponDTO.getCouponStock())
                .code(GeneratedRandomNumber.generateReferralCode(7))
                .expirationPeriod(LocalDate.from(LocalDateTime.now().plusWeeks(4)))
                .discount(couponDTO.getDiscount())
                .purchaseLimitAmount(couponDTO.getPurchaseLimitAmount())
                .couponCreatedTime(LocalDate.now())
                .isDeleted(false)
                .active(true)
                .build();
    }

    static  Coupon updateCoupon(CouponDTO couponDTO , Coupon coupon){
        Coupon coupon1 = new Coupon();
        coupon1.setCouponStock(couponDTO.getCouponStock());
        coupon1.setCouponModifiedTime(LocalDate.now());
        coupon1.setCouponStock(couponDTO.getCouponStock());
        coupon1.setDiscount(couponDTO.getDiscount());
        coupon1.setPurchaseLimitAmount(couponDTO.getPurchaseLimitAmount());
        coupon1.setExpirationPeriod(couponDTO.getExpirationPeriod());
        return coupon1;
    }

    static CouponResponseDTO convertCouponToCouponResponseDTO(Coupon coupon){
        return CouponResponseDTO.builder()
                .id(coupon.getId())
                .purchaseLimitAmount(coupon.getPurchaseLimitAmount())
                .active(coupon.getActive())
                .code(coupon.getCode())
                .couponStock(coupon.getCouponStock())
                .discount(coupon.getDiscount())
                .expirationPeriod(coupon.getExpirationPeriod())
                .isDeleted(coupon.isDeleted())
                .build();
    }
}

