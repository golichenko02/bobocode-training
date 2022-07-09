package com.bobocode.service;

import com.bobocode.annotation.Bean;

@Bean(name = "uaGreeting")
public class GreetingServiceUA extends GreetingService {

    @Override
    public String sayHello() {
        return "Glory to Ukraine!";
    }
}
