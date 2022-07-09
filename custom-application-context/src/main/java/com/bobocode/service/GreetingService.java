package com.bobocode.service;

import com.bobocode.annotation.Bean;

@Bean
public class GreetingService {

    public String sayHello(){
        return "Hello!";
    }
}
