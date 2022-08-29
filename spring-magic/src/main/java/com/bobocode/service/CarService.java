package com.bobocode.service;

import com.magic.annotation.Trimmed;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Trimmed
@Service
@Log4j2
public class CarService implements VehicleService{
    @Override
    public void move(String name) {
       log.info("{}: driving...", name);
    }
}
