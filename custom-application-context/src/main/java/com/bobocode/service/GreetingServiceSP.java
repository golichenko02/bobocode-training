package com.bobocode.service;

import com.bobocode.annotation.Bean;

@Bean(name = "spanishGreeting")
public class GreetingServiceSP extends GreetingService {
    @Override
    public String sayHello() {
        return "Hola!";
    }
}