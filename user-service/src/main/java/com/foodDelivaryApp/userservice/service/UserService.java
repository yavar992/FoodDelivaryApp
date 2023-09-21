package com.foodDelivaryApp.userservice.service;

import com.foodDelivaryApp.userservice.entity.User;

public interface UserService {
    String saveUser(User user);

    boolean userAlreadyExistByEmailOrUserName(String email, String username);
}
