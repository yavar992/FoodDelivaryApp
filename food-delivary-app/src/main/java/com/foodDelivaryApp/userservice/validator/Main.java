package com.foodDelivaryApp.userservice.validator;

import com.foodDelivaryApp.userservice.util.GeneratedRandomNumber;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

public class Main {



    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(" yavaralikhan02@gmail.com -- " + passwordEncoder.encode("Password1@23"));
        System.out.println(" yavarkhan886500@example.com -- " + passwordEncoder.encode("Password123"));
        System.out.println(GeneratedRandomNumber.generateReferralCode(6));
        System.out.println("RestaurantOwner_Password -- " + passwordEncoder.encode("Yavar992"));

        System.out.println( "15 minute next from now " +  Instant.now().plusMillis(9000000));
    }
}
