package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryGuyRepo extends JpaRepository<DeliveryGuy , Long> {

    @Query(value = "SELECT * FROM deliveryGuy WHERE email=?1 OR username=?2 ",nativeQuery = true)
    DeliveryGuy findByEmailOrUserName(String email, String username);

    @Query(value = "SELECT * FROM `deliveryGuy` WHERE referralCode = ?1" , nativeQuery = true)
    DeliveryGuy findUserByReferralCode(String referralCode);

    @Query(value = "SELECT * FROM `deliveryGuy` WHERE referralCode = ?1" , nativeQuery = true)
    DeliveryGuy findDeliveryGuyWithReferralCode(String referralCode);
}
