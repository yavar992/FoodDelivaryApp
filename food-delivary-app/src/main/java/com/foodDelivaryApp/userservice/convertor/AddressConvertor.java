package com.foodDelivaryApp.userservice.convertor;

import com.foodDelivaryApp.userservice.DTO.AddressDTO;
import com.foodDelivaryApp.userservice.entity.Address;

public interface AddressConvertor {

    static Address convertAddressDtoToAddress(AddressDTO addressDTO){
        return Address.builder()
                .area(addressDTO.getArea())
                .pin(addressDTO.getPin())
                .city(addressDTO.getCity())
                .defaultAddress(addressDTO.isDefaultAddress())
                .flat(addressDTO.getFlat())
                .state(addressDTO.getState())
                .town(addressDTO.getTown())
                .landmark(addressDTO.getLandmark())
                .isDeleted(false)
                .build();
    }
}
