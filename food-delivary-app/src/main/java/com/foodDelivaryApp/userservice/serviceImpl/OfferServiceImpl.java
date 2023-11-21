package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.repository.OfferRepo;
import com.foodDelivaryApp.userservice.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepo offerRepo;
}
