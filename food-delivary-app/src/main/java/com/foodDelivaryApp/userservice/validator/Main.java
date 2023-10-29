package com.foodDelivaryApp.userservice.validator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Main {



    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(" encoded Password " + passwordEncoder.encode("Password1@23"));
        System.out.println(" encoded Password " + passwordEncoder.encode("Password123"));
    }
}
