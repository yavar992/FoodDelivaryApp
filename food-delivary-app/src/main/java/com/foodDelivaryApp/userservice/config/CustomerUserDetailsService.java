package com.foodDelivaryApp.userservice.config;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.service.IRestaurantOwnerService;
import com.foodDelivaryApp.userservice.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRestaurantOwnerService restaurantsOwnerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean ownerExists = restaurantsOwnerService.ownerExistByEmail(email);
        Optional<User> customer = null;//userService.userAlreadyExistByEmailOrUserName(email, email);

        if (ownerExists){
            RestaurantOwner restaurantOwner = restaurantsOwnerService.findByEmail(email);
            Set<GrantedAuthority> authorities = restaurantOwner.getRoles()
                    .stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());
               log.info("authorities {}" , authorities);
               log.info("simpleGrantedAuthorities {}", authorities.stream().map((role)->new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toSet()));
               return new org.springframework.security.core.userdetails.User(
                    restaurantOwner.getEmail(),
                    restaurantOwner.getPassword(),
                    authorities
            );
        } else if (customer.isPresent()){
            User customer1 = customer.get();
            Set<GrantedAuthority> authorities = customer1.getRoles()
                    .stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());
            log.info("authorities {}" , authorities);

            return new org.springframework.security.core.userdetails.User(
                    customer1.getEmail(), 
                    customer1.getPassword(), 
                    authorities);
        }


        throw new UsernameNotFoundException("Customer not found for the username " + email);
    }
}
