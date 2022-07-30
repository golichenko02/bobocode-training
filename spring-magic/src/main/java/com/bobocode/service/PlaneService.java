package com.bobocode.service;

import com.magic.annotation.Trimmed;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Trimmed
@Service
@Log4j2
public class PlaneService implements VehicleService {

    @Override
    public void move(String name) {
        log.info("{}: flying...", name);
    }

    @Override
    public String getInfo() {
        return "       \nPlane       ";
    }
}
