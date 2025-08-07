package com.foodDelivaryApp.userservice.serviceImpl;


import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.exceptionHandling.RefreshTokenExpirationException;
import com.foodDelivaryApp.userservice.jwt.RefreshToken;
import com.foodDelivaryApp.userservice.repository.DeliveryGuyRepo;
import com.foodDelivaryApp.userservice.repository.RefreshTokenRepo;
import com.foodDelivaryApp.userservice.repository.RestaurantsOwnerRepo;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestaurantsOwnerRepo restaurantsOwnerRepo;

    @Autowired
    private DeliveryGuyRepo deliveryGuyRepo;

    public RefreshToken createRefreshToken(String username){
        User user = userRepo.findUserByEmail(username);
            RefreshToken refreshToken1 = findByUserId(user.getId());
        if (refreshToken1!=null){
            refreshTokenRepo.delete(refreshToken1);
        }
        RefreshToken refreshToken =  RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1000000))
                .user(user)
                .build();
          return  refreshTokenRepo.save(refreshToken);
    }

    public RefreshToken createRefreshTokenForRestaurantOwner(String email){
        RestaurantOwner restaurantOwner = restaurantsOwnerRepo.findByEmail(email).get();
//        log.info("restaurant owner {} " , restaurantOwner);
        RefreshToken refreshToken1 = findByRestaurantOwnerId(restaurantOwner.getId());
        if (refreshToken1!=null){
            refreshTokenRepo.delete(refreshToken1);
        }
        RefreshToken refreshToken =  RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(200000))
                .user(null)
                .restaurantOwner(restaurantsOwnerRepo.findByEmail(email).get())
                .build();
        return  refreshTokenRepo.save(refreshToken);
    }

    public RefreshToken createRefreshTokenForDeliveryBoy(String email){
        DeliveryGuy deliveryGuy = deliveryGuyRepo.findByEmail(email);
        RefreshToken refreshToken1 = findByDeliveryBoyId(deliveryGuy.getId());
        if (refreshToken1!=null){
            refreshTokenRepo.delete(refreshToken1);
        }
        log.info("user {} ", deliveryGuy);
        RefreshToken refreshToken =  RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1000000))
                .deliveryGuy(deliveryGuy)
                .build();
        return  refreshTokenRepo.save(refreshToken);
    }


    public  RefreshToken findByToken(String token){
        Optional<RefreshToken> refreshToken = refreshTokenRepo.findByToken(token);
        if (refreshToken.isEmpty()){
            throw new RefreshTokenExpirationException(token + " Refresh token was expired. Please make a new signin request");
        }
        return refreshToken.get();
    }
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new RefreshTokenExpirationException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    public RefreshToken findByUserId(Long id){
        RefreshToken refreshToken = refreshTokenRepo.findByUserId(id);
        return refreshToken;
    }

    public RefreshToken findByRestaurantOwnerId(Long id){
        RefreshToken refreshToken = refreshTokenRepo.findByRestaurantOwnerId(id);
        return refreshToken;
    }

    public RefreshToken findByDeliveryBoyId(Long id){
        RefreshToken refreshToken = refreshTokenRepo.findByDeliveryBoyId(id);
        return refreshToken;
    }

    public void invalidateRefreshToken(String refreshToken) {
        Optional<RefreshToken> token = refreshTokenRepo.findByToken(refreshToken);
        if (token.isPresent()) {
            refreshTokenRepo.delete(token.get());
        }
    }



}
