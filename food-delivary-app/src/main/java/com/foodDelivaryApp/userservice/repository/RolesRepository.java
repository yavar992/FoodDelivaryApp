package com.foodDelivaryApp.userservice.repository;


import com.foodDelivaryApp.userservice.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {


    @Query(value = "SELECT * FROM `roles` WHERE roles = ?1 " , nativeQuery = true)
    Optional<Roles> findByName(String roles);
}
