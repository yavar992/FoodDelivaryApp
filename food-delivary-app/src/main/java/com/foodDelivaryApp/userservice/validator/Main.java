package com.foodDelivaryApp.userservice.validator;

import com.foodDelivaryApp.userservice.util.OTPUtil;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Integer ab = 2343;
        Integer vf = 2343;
        System.out.println(ab==vf);
        System.out.println(ab.equals(vf));

        List<String> s = Arrays.asList("HELLO","HI","I","ACD","ED");
        String dd = "HELLO";
        String ans =  check(s , dd);
        System.out.println(ans);
        System.out.println(Math.random()*900+111);
        Integer random = (int) (Math.random()*900+111);
        System.out.println(random);
        System.out.println(OTPUtil.random3Digit());
    }

    public static String check(List<String> str , String s){
        if (str.contains(s)){
            return "yavar";
        }
        return "hi";
    }
}
