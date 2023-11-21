package com.foodDelivaryApp.userservice.serviceImpl;


import com.foodDelivaryApp.userservice.repository.WalletRepo;
import com.foodDelivaryApp.userservice.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepo walletRepo;

}
