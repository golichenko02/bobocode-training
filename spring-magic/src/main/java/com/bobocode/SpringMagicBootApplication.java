package com.bobocode;

import com.bobocode.service.CarService;
import com.bobocode.service.PlaneService;
import com.bobocode.service.VehicleService;
import com.magic.annotation.EnableStringTrimming;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableStringTrimming
@Log4j2
public class SpringMagicBootApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringMagicBootApplication.class, args);
        VehicleService planeService = context.getBean(PlaneService.class);
        VehicleService carService = context.getBean(CarService.class);

        planeService.move( "                         Boeing                                             \n\n");
        carService.move("Tesla");
        log.info(planeService.getInfo());
        log.info(carService.getInfo());
    }
}
