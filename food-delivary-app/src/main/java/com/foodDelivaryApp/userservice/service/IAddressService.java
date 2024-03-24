package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.dto.AddressDTO;
import com.foodDelivaryApp.userservice.entity.User;

public interface IAddressService {
    String saveAddress(AddressDTO addressDTO, User user);
}
