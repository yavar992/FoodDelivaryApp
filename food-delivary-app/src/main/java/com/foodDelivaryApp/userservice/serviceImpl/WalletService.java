package com.foodDelivaryApp.userservice.serviceImpl;


import com.foodDelivaryApp.userservice.repository.WalletRepository;
import com.foodDelivaryApp.userservice.service.IWalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class WalletService implements IWalletService {

    @Autowired
    private WalletRepository walletRepository;

}
