package com.foodDelivaryApp.userservice.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodDelivaryApp.userservice.convertor.AddressConvertor;
import com.foodDelivaryApp.userservice.dto.AddressDTO;
import com.foodDelivaryApp.userservice.entity.Address;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.repository.AddressRepo;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.IAddressService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddressService implements IAddressService {

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public String saveAddress(AddressDTO addressDto, User user) {
        Address address = AddressConvertor.MAPPER.toAddress(addressDto);
        address.setUser(user);

        Address address1 = addressRepo.findDefaultAddress(user.getId());
        if (addressDto.isDefaultAddress() && address1 != null){  // this cases will check if a user already have a default address , if yes then it will update the new address as default address
            updateExistingDefaultAddress(address1);
            saveNewAddress(address);
            updateDefaultAddressForUser(user, address);
            return "default address updated successfully !";
        }

        if (addressDto.isDefaultAddress()){  //this case will check if the user choose the default address and it found no other address as default address for the user
            user.setDefaultAddress(address); // then it will add the address as default address
            userRepo.saveAndFlush(user);
            addressRepo.saveAndFlush(address);
            return "address added successfully with default address !";
        }

        addressRepo.saveAndFlush(address);  // case 3 -- this case will just add the address for the user it will not check for the default address for the user
        return "address saved successfully";

    }

    private void updateExistingDefaultAddress(Address existingDefaultAddress) {
        existingDefaultAddress.setDefaultAddress(false);
        addressRepo.saveAndFlush(existingDefaultAddress);
    }

    private Address saveNewAddress(Address newAddress) {
        return addressRepo.saveAndFlush(newAddress);
    }

    private void updateDefaultAddressForUser(User user, Address newDefaultAddress) {
        user.setDefaultAddress(newDefaultAddress);
        userRepo.saveAndFlush(user);
    }

}
