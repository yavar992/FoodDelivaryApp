package com.foodDelivaryApp.userservice.util;

import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    @Autowired
    private UserService userService;

    public User authenticatedUser(Authentication authentication){
        String username = authentication.getName();
        return userService.findUserByEmail(username);
    }






}


