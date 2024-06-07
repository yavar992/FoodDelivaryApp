package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.AddressDTO;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.service.AddressService;
import com.foodDelivaryApp.userservice.service.UserService;
import com.foodDelivaryApp.userservice.util.CommonUtil;
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
    private AddressService addressService;



    @Autowired
    private CommonUtil commonUtil;

    @PostMapping
    public ResponseEntity<?> addAddress(@Valid @RequestBody AddressDTO addressDTO , Authentication authentication){
      User user = commonUtil.authenticatedUser(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body( addressService.saveAddress(addressDTO , user));
    }

    @PostMapping("/hello")
    public String test(){
        return "helllllo";
    }
}
