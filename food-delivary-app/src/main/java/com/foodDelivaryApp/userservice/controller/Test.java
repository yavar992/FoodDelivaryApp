package com.foodDelivaryApp.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @GetMapping("/test2")
    public String test2(){
        return "hello this is test 2";
    }

}
