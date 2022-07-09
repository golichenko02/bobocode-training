package com.bobocode.service;

import com.bobocode.annotation.Bean;
import com.bobocode.annotation.Inject;
import lombok.Data;

@Bean
@Data
public class AdditionalService {

    @Inject
    private GreetingServiceUA greetingServiceUA;
}
