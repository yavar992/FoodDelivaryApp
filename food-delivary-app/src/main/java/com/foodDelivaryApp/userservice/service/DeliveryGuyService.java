package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.entity.DeliveryGuy;

public interface DeliveryGuyService {
    boolean userAlreadyExistByEmailOrUserName(String email, String username);

    String saveDeliveryGuy(DeliveryGuy deliveryGuy, String referralCode);
}
