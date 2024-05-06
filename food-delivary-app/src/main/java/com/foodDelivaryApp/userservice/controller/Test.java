package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.DTO.UserLoginDetails;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class Test {

    @Autowired
    private UserRepo userRepo;


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

    @RequestMapping("/")
    public String homePage(){
        return "home";
    }


}
