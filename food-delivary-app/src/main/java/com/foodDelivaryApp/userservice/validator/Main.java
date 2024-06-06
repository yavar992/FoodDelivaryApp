package com.foodDelivaryApp.userservice.validator;

import com.ctc.wstx.util.StringUtil;
import com.foodDelivaryApp.userservice.Enums.DeliveryGuyStatusEnum;
import com.foodDelivaryApp.userservice.util.GeneratedRandomNumber;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Random;

public class Main {

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void hi(String statusUpdate){
        DeliveryGuyStatusEnum status;
        try {
            status = DeliveryGuyStatusEnum.valueOf(statusUpdate.toUpperCase());
            System.out.println("STAUTS : " + status);
        } catch (IllegalArgumentException e) {
            System.out.println("error");
        }
    }


    public static void main(String[] args) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(" yavaralikhan02@gmail.com -- " + passwordEncoder.encode("Password1@23"));
        System.out.println(" yavarkhan886500@example.com -- " + passwordEncoder.encode("Password123"));
        System.out.println(GeneratedRandomNumber.generateReferralCode(6));
        System.out.println("RestaurantOwner_Password -- " + passwordEncoder.encode("Yavar992"));

        System.out.println( "15 minute next from now " +  Instant.now().plusMillis(9000000));

        hi("ACTIVE");

    }
}
