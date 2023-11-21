package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.WalletHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletHistoryRepo extends JpaRepository<WalletHistory,Long> {
}
