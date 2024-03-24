package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.convertor.AddressConvertor;
import com.foodDelivaryApp.userservice.dto.AddressDTO;
import com.foodDelivaryApp.userservice.entity.Address;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.IAddressService;
import com.foodDelivaryApp.userservice.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/users/address")
@RestController
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<?> addAddress(@Valid @RequestBody AddressDTO addressDTO , Authentication authentication){
        String username = authentication.getName();
        System.out.println("Authenticated user: " + username);
        User user = userService.findUserByEmail(username);
        return ResponseEntity.status(HttpStatus.CREATED).body( addressService.saveAddress(addressDTO , user));
    }

    @PostMapping("/hello")
    public String test(){
        return "helllllo";
    }
}
