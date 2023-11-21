package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.repository.WalletHistoryRepo;
import com.foodDelivaryApp.userservice.service.WalletHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class WalletHistoryServiceImpl implements WalletHistoryService {

    @Autowired
    private WalletHistoryRepo walletHistoryRepo;
}
