package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet,Long> {
}
