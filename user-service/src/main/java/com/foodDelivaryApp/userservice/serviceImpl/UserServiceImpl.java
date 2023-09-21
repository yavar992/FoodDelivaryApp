package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import com.foodDelivaryApp.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public String saveUser(User user) {
        userRepo.save(user);
        return "User Registered SuccessFully !";
    }

    @Override
    public boolean userAlreadyExistByEmailOrUserName(String email, String username) {
        User user = userRepo.findByEmailOrUserName(email, username);
        return user != null;
    }

}
