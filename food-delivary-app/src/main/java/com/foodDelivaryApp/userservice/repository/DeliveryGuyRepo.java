package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DeliveryGuyRepo extends JpaRepository<DeliveryGuy , Long> {

    @Query(value = "SELECT * FROM deliveryGuy WHERE email=?1 OR username=?2 ",nativeQuery = true)
    DeliveryGuy findByEmailOrUserName(String email, String username);

    @Query(value = "SELECT * FROM `deliveryGuy` WHERE referralCode = ?1" , nativeQuery = true)
    DeliveryGuy findUserByReferralCode(String referralCode);

    @Query(value = "SELECT * FROM `deliveryGuy` WHERE referralCode = ?1" , nativeQuery = true)
    DeliveryGuy findDeliveryGuyWithReferralCode(String referralCode);

    @Query(value = "SELECT * FROM `deliveryguy` WHERE otp = ?1" , nativeQuery = true)
    DeliveryGuy findByOTP(Integer otp);

    @Query(value = "SELECT DR.guyWhoReferrerCode_id FROM `deliveryguy` dy LEFT JOIN deliver_referrals  DR ON dy.id = DR.guyWhoSignup_id=?1" , nativeQuery = true)
    Long findUserWhoSignUp(long userId);

    @Query(value =  "SELECT * FROM deliveryguy WHERE email = ?1"  , nativeQuery = true)
    DeliveryGuy findByEmail(String email);

    @Query(value =  "SELECT * FROM deliveryguy WHERE email = ?1"  , nativeQuery = true)
    Optional<DeliveryGuy> findByDeliveryBoyEmail(String email);

    @Query(value =  "SELECT * FROM deliveryguy WHERE username=?1 OR email = ?2 "  , nativeQuery = true)
    DeliveryGuy findByUsernameOrEmail(String username, String email);
}
