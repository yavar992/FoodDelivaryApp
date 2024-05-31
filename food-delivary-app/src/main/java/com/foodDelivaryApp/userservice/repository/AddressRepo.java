package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepo extends JpaRepository<Address , Long> {
    @Query(value = "SELECT * FROM `address` WHERE defaultAddress = 1 AND user_id = ?1" , nativeQuery = true)
    Address findDefaultAddress(Long id);

    @Query(value = "SELECT * FROM `address` WHERE user_id = 49 AND defaultAddress =1 " , nativeQuery = true)
    Address findByUserIdWhereDefaultAddressIs(long userId);

}
