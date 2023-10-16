package com.foodDelivaryApp.userservice.foodCommon;

import com.foodDelivaryApp.userservice.exceptionHandling.UnverifiedUserException;

public class HappyMealCommon {

    public static void isUserVerified(Boolean isVerified){
        if (!isVerified){
            throw new UnverifiedUserException("");
        }
    }
}



