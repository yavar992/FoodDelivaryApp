package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User , Long> {

    @Query(value = "SELECT * FROM user WHERE email=?1 OR username=?2 ",nativeQuery = true)
    User findByEmailOrUserName(String email, String username);
}
