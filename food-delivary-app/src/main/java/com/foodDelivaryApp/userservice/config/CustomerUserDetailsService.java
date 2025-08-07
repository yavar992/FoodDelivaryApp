package com.foodDelivaryApp.userservice.config;

import com.foodDelivaryApp.userservice.entity.DeliveryGuy;
import com.foodDelivaryApp.userservice.entity.RestaurantOwner;
import com.foodDelivaryApp.userservice.entity.User;
import com.foodDelivaryApp.userservice.repository.DeliveryGuyRepo;
import com.foodDelivaryApp.userservice.repository.RestaurantsOwnerRepo;
import com.foodDelivaryApp.userservice.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestaurantsOwnerRepo restaurantsOwnerRepo;

    @Autowired
    private DeliveryGuyRepo
            deliveryGuyRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<RestaurantOwner> restaurantOwner = restaurantsOwnerRepo.findByUsernameOrEmail(email);
        log.info("restaurantOwner {}" , restaurantOwner);
        Optional<User> customer = userRepo.findByUsernameOrEmail(email , email);
        log.info("user {}", customer);

        Optional<DeliveryGuy> deliveryGuy = deliveryGuyRepo.findByDeliveryBoyEmail(email);

        if (restaurantOwner.isPresent()){
            RestaurantOwner restaurantOwner1 = restaurantOwner.get();
            Set<GrantedAuthority> authorities = restaurantOwner1.getRoles()
                    .stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getRoles())).collect(Collectors.toSet());
               log.info("authorities {}" , authorities);
               log.info("simpleGrantedAuthorities {}", authorities.stream().map((role)->new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toSet()));
               return new org.springframework.security.core.userdetails.User(
                    restaurantOwner1.getEmail(),
                    restaurantOwner1.getPassword(),
                    authorities
            );
        }

        if (deliveryGuy.isPresent()){
            DeliveryGuy deliveryGuy1 = deliveryGuy.get();
            Set<GrantedAuthority> authorities = deliveryGuy1.getRoles()
                    .stream()
                    .map((role)->new SimpleGrantedAuthority(role.getRoles())).collect(Collectors.toSet());
            log.info("simpleGrantedAuthorities {}", authorities.stream().map((role)->new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toSet()));
            return new org.springframework.security.core.userdetails.User(
                    deliveryGuy1.getEmail(),
                    deliveryGuy1.getPassword(),
                    authorities
            );

        }

           else if (customer.isPresent()){
                User customer1 = customer.get();

                Set<GrantedAuthority> authorities = customer1.getRoles()
                        .stream()
                        .map((role) -> new SimpleGrantedAuthority(role.getRoles())).collect(Collectors.toSet());
                log.info("authorities {}" , authorities);
                return new org.springframework.security.core.userdetails.User(
                        customer1.getEmail(),
                        customer1.getPassword(),
                        authorities
                );
            }


        throw new UsernameNotFoundException("User not found for the username " + email);
    }
}
