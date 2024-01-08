package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.DTO.CouponDTO;
import com.foodDelivaryApp.userservice.DTO.CouponResponseDTO;
import com.foodDelivaryApp.userservice.convertor.CouponConvertor;
import com.foodDelivaryApp.userservice.entity.Coupon;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.exceptionHandling.InvalidUserException;
import com.foodDelivaryApp.userservice.repository.CouponRepo;
import com.foodDelivaryApp.userservice.repository.RestaurantsOwnerRepo;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestaurantsOwnerRepo restaurantsOwnerRepo;

    @Override
    public Coupon createCoupon(CouponDTO coupon , String name) {
        Coupon coupon1 = CouponConvertor.convertCouponDTOToCoupon(coupon);
        Optional<RestaurantOwner> user = restaurantsOwnerRepo.findByEmail(name);
        RestaurantOwner restaurantOwner = user.get();
        coupon1.setRestaurantOwner(restaurantOwner);
        return couponRepo.saveAndFlush(coupon1);
    }

    @Override
    public String extendCouponDuration(Long id , LocalDate ExpirationPeriod) {
        Coupon coupon = couponRepo.findById(id).orElseThrow(()->new IllegalArgumentException("No Coupon found for the id " + id));
        coupon.setCouponCreatedTime(ExpirationPeriod);
        couponRepo.saveAndFlush(coupon);
        return "Coupon duration extend successfully !";
    }

    @Override
    public String updateCoupon(Long id, CouponDTO couponDTO , String email) {
        Coupon coupon = couponRepo.findById(id).orElseThrow(()->new IllegalArgumentException("No Coupon found for the id " + id));
        Coupon coupon1 = CouponConvertor.updateCoupon(couponDTO,coupon);
        Optional<RestaurantOwner> restaurantOwner = restaurantsOwnerRepo.findByEmail(email);
        RestaurantOwner restaurantOwner1 = restaurantOwner.get();
        coupon1.setRestaurantOwner(restaurantOwner1);
        couponRepo.saveAndFlush(coupon1);
        return "Coupon updated Successfully !";
    }

    @Override
    public String deleteCoupon(Long id) {
        Coupon coupon = couponRepo.findById(id).orElseThrow(()->new IllegalArgumentException("No Coupon found for the id " + id));
        couponRepo.delete(coupon);
        return "Coupon deleted successfully !";
    }

    @Override
    public CouponResponseDTO couponById(Long id) {
        Coupon coupon = couponRepo.findById(id).orElseThrow(()->new IllegalArgumentException("No Coupon found for the id " + id));
        return CouponConvertor.convertCouponToCouponResponseDTO(coupon);
    }

    @Override
    public List<CouponResponseDTO> allCoupons() {
        List<Coupon> coupons = couponRepo.findAll();
        if(coupons.isEmpty()){
            throw new InvalidUserException("NO COUPON FOUND IN THE DB");
        }
        return coupons.stream().map(CouponConvertor::convertCouponToCouponResponseDTO).collect(Collectors.toList());
    }


}
