package com.foodDelivaryApp.userservice.controller;

import com.foodDelivaryApp.userservice.service.RestaurantsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantsService restaurantsService;


}
