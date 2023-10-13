package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User , Long> {

    @Query(value = "SELECT * FROM user WHERE email=?1 OR username=?2 ",nativeQuery = true)
    User findByEmailOrUserName(String email, String username);


    @Query(value = "SELECT * FROM user WHERE otp=?1" , nativeQuery = true)
    User findByOTP(Integer otp);

    User findByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE email=?1 OR username=?2 " , nativeQuery = true)
    Optional<User> findByUsernameOrEmail(String email ,  String username);
}
