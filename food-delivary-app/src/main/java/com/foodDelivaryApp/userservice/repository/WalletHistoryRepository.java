package com.foodDelivaryApp.userservice.repository;

import com.foodDelivaryApp.userservice.entity.WalletHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletHistoryRepository extends JpaRepository<WalletHistory,Long> {
}
