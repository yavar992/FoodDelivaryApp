package com.foodDelivaryApp.userservice.util;

import com.foodDelivaryApp.userservice.DTO.AuthDTO;
import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.repository.DeliveryGuyRepo;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class LoginRateLimitApiUtil {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DeliveryGuyRepo deliveryGuyRepo;


    public String restartApiHittingRate(User user) {
        int apiHitCount = userService.getApiHitCount(user.getEmail());
        if (apiHitCount==0){
            Instant currentTime = Instant.now();
            Instant targetTime = Instant.now().plusSeconds(9000);
            user.setFirstTimeApiHittingTime(currentTime);
            user.setTargetTime(targetTime);
            userRepo.saveAndFlush(user);
        }
        Instant targetTimeOfUserFromDB = userService.getUserApiHittingTargetTime(user.getEmail());
        if (targetTimeOfUserFromDB.isBefore(Instant.now())){
            user.setApiHitCount(1);
            userRepo.saveAndFlush(user);

        }

        return "";
    }

    public ResponseEntity<?> checkAndHandleApiHitLimit(User user) {
        // Check if the time elapsed since the first API hit exceeds 10 minutes
        Instant currentTime = Instant.now();
        Instant firstHitTime = user.getFirstTimeApiHittingTime();
        long minutesElapsed = Duration.between(firstHitTime, currentTime).toMinutes();
        if (minutesElapsed < 10) {
            // Block the user's account for the next 10 minutes
            user.setBlocked(true);
            user.setTargetTime(currentTime.plus(Duration.ofMinutes(10)));
            userRepo.saveAndFlush(user);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account is blocked for the next 10 minutes due to multiple unsuccessful attempts.");
        } else {
            // Reset API hit count and first hit time as the time limit has passed
            user.setApiHitCount(0);
            user.setFirstTimeApiHittingTime(null);
            userRepo.saveAndFlush(user);
        }
        return null; // Or you can return a ResponseEntity with appropriate message
    }


    public void incrementApiHitCount(User user) {
        user.setApiHitCount(user.getApiHitCount() + 1);
        if (user.getFirstTimeApiHittingTime() == null) {
            user.setFirstTimeApiHittingTime(Instant.now());
        }
        userRepo.saveAndFlush(user);
    }

    // Method to handle API hit count logic
    public void handleApiHitCount(User user) {
        if (user.getFirstTimeApiHittingTime() == null || user.getFirstTimeApiHittingTime().plusSeconds(15 * 60).isBefore(Instant.now())) {
            // If the last API hit was more than 15 minutes ago, reset hit count
            user.setApiHitCount(0);
            user.setBlocked(false);
            user.setFirstTimeApiHittingTime(Instant.now());
        }

        user.setApiHitCount(user.getApiHitCount() + 1); // Increment hit count

        // If hit count exceeds 5, block the user for the next 15 minutes
        if (user.getApiHitCount() > 5) {
            user.setBlocked(true);
            user.setTargetTime(Instant.now().plusSeconds(15 * 60));
        }

        // Save the updated user back to the database
        userRepo.save(user);
    }

    public void handleApiHitCountForDeliveryGuy(DeliveryGuy deliveryGuy) {
        if (deliveryGuy.getFirstTimeApiHittingTime() == null || deliveryGuy.getFirstTimeApiHittingTime().plusSeconds(2 * 60).isBefore(Instant.now())) {
            // If the last API hit was more than 15 minutes ago, reset hit count
            deliveryGuy.setApiHitCount(0);
            deliveryGuy.setBlocked(false);
            deliveryGuy.setFirstTimeApiHittingTime(Instant.now());
        }

        deliveryGuy.setApiHitCount(deliveryGuy.getApiHitCount() + 1); // Increment hit count

        // If hit count exceeds 5, block the user for the next 15 minutes
        if (deliveryGuy.getApiHitCount() > 5) {
            deliveryGuy.setBlocked(true);
            deliveryGuy.setTargetTime(Instant.now().plusSeconds(15 * 60));
        }

        // Save the updated user back to the database
        deliveryGuyRepo.save(deliveryGuy);
    }
}
