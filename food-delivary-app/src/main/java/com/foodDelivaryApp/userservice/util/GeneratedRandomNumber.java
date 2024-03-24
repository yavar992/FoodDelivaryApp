package com.foodDelivaryApp.userservice.util;

import java.util.Random;

public interface GeneratedRandomNumber {

     public static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

     static String generateReferralCode(int  length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHAR_SET.length());
            char randomChar = CHAR_SET.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
