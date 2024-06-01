package com.foodDelivaryApp.userservice.serviceImpl;

import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import com.foodDelivaryApp.userservice.entity.DeliveryGuyRating;
import com.foodDelivaryApp.userservice.entity.Roles;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.event.UserRegisterationEvent;
import com.foodDelivaryApp.userservice.exceptionHandling.UserNotFoundException;
import com.foodDelivaryApp.userservice.repository.DeliveryGuyRepo;
import com.foodDelivaryApp.userservice.repository.RolesRepository;
import com.foodDelivaryApp.userservice.service.DeliveryGuyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class DeliveryGuyServiceImpl implements DeliveryGuyService {

    @Autowired
    private DeliveryGuyRepo deliveryGuyRepo;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public boolean userAlreadyExistByEmailOrUserName(String email, String username) {
        DeliveryGuy deliveryGuy = deliveryGuyRepo.findByEmailOrUserName(email, username);
        return deliveryGuy != null;
    }

    @Override
    public String saveDeliveryGuy(DeliveryGuy deliveryGuy, String referralCode) {
        if (referralCode!=null){
            DeliveryGuy deliveryGuy1 = deliveryGuyRepo.findUserByReferralCode(referralCode);
            if (deliveryGuy1==null){
                throw new UserNotFoundException("Invalid referral code , Please enter the correct referral code  " );
            }
        }
        Set<Roles> rolesSet = new HashSet<>();
        Optional<Roles> roles = rolesRepository.findByName("ROLE_DELIVERY");
        Roles roles1;
        if (roles.isPresent()){
            roles1 = roles.get();
            rolesSet.add(roles1);
        }
        else{
            throw new UserNotFoundException("Invalid Role !!!");
        }

        log.info("Roles {}" , roles1);
        rolesSet.add(roles1);
        deliveryGuy.setRoles(rolesSet);
        UserRegisterationEvent userRegisterationEvent = new UserRegisterationEvent(deliveryGuy);
        applicationEventPublisher.publishEvent(userRegisterationEvent);

        if (referralCode!=null){
            Set<DeliveryGuy> deliveryGuys = new HashSet<>();
            DeliveryGuy deliveryGuy1 = deliveryGuyRepo.findDeliveryGuyWithReferralCode(referralCode);
            deliveryGuys.add(deliveryGuy1);
            deliveryGuy.setReferredDeliveryGuy(deliveryGuys);
        }

        deliveryGuyRepo.saveAndFlush(deliveryGuy);

        return "DeliveryGuy Registered SuccessFully !";
    }
}
