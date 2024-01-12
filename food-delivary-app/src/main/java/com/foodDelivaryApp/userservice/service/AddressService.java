package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.DTO.AddressDTO;
import com.foodDelivaryApp.userservice.entity.User;

public interface AddressService {
    String saveAddress(AddressDTO addressDTO, User user);
}
