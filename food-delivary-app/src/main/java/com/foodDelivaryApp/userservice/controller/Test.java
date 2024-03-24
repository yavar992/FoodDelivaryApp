package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.dto.UserLoginDetails;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String test2(){
        return "hello this is test 2";
    }

    @GetMapping("/t2")
    public User user(){
        return userRepo.findByEmail("yavaralikhan02@example.com");
    }

    @GetMapping("/user")
    public User getUser(@RequestParam("email") String email){
        return userRepo.findByEmail(email);
    }

    @GetMapping("/t3")
    public UserLoginDetails getUserDetail(@RequestParam("email") String email){
        Object[] result =   userRepo.findByEmailAndVerifiedDetails(email);
         return new UserLoginDetails((String) result[0] , (Boolean) result[1]);

    }

}
