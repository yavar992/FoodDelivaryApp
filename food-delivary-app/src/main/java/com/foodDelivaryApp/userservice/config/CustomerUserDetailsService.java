//package com.foodDelivaryApp.userservice.config;
//
//import com.foodDelivaryApp.userservice.entity.User;
//import com.foodDelivaryApp.userservice.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomerUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepo userRepo;
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        Optional<User> customer = userRepo.findByUsernameOrEmail(email , email);
//        if (customer.isEmpty()){
//            throw new UsernameNotFoundException("Customer not found for the username " + email);
//        }
//        User customer1 = customer.get();
//
//        Set<GrantedAuthority> authorities = customer1.getRoles()
//                .stream()
//                .map((role) -> new SimpleGrantedAuthority(role.getRoles())).collect(Collectors.toSet());
//        return new org.springframework.security.core.userdetails.User(
//                customer1.getEmail(),
//                customer1.getUsername(),
//                authorities
//        );
//    }
//}
