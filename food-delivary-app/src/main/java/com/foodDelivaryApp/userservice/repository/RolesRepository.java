package com.foodDelivaryApp.userservice.repository;


import com.foodDelivaryApp.userservice.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query(value = "SELECT r FROM Roles r WHERE r.role = :role ")
    Optional<Roles> findByName(@Param("role") String roles);
}
