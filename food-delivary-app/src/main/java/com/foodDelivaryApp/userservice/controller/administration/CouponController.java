package com.foodDelivaryApp.userservice.controller.administration;

import com.foodDelivaryApp.userservice.dto.CouponDTO;
import com.foodDelivaryApp.userservice.dto.CouponResponseDTO;
import com.foodDelivaryApp.userservice.entity.Coupon;
import com.foodDelivaryApp.userservice.foodCommon.HappyMealConstant;
import com.foodDelivaryApp.userservice.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/coupon")
public class CouponController {

    @Autowired
    private ICouponService couponService;

    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody CouponDTO couponDTO , Authentication authentication){

        try {
            String name = authentication.getName();
            Coupon coupon1 = couponService.createCoupon(couponDTO , name);
            if (coupon1!=null){
                return ResponseEntity.status(HttpStatus.OK).body(coupon1);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @PostMapping("/extendCouponDuration/{id}")
    public ResponseEntity<?> extendCouponDuration(@PathVariable("id") Long id , CouponDTO couponDTO ){
        try {
            String extendMessage = couponService.extendCouponDuration(id , couponDTO.getExpirationPeriod());
            if (extendMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(extendMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @PostMapping("/updateCoupon/{id}")
    public ResponseEntity<?> updateCoupon(@PathVariable("id") Long id , @RequestBody CouponDTO couponDTO , Authentication authentication){
        try {
            String email = authentication.getName();
            String updateMessage = couponService.updateCoupon(id,couponDTO , email);
            if (updateMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(updateMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }


    @DeleteMapping("/deleteCoupon/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable("id") Long id){
        try {
            String updateMessage = couponService.deleteCoupon(id);
            if (updateMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(updateMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

    @GetMapping("/coupon/{id}")
    public ResponseEntity<?> getCouponById(@PathVariable("id") Long id){
        try {
            CouponResponseDTO coupon = couponService.couponById(id);
            if (coupon!=null){
                return ResponseEntity.status(HttpStatus.OK).body(coupon);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }


    @GetMapping("/coupons")
    public ResponseEntity<?> getAllCoupon(){
        try {
            List<CouponResponseDTO> coupon = couponService.allCoupons();
            if (!coupon.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(coupon);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HappyMealConstant.SOMETHING_WENT_WRONG);
    }

}
