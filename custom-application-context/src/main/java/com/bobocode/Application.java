package com.bobocode;

import com.bobocode.context.ApplicationContext;
import com.bobocode.context.PackageScanApplicationContext;
import com.bobocode.service.AdditionalService;
import com.bobocode.service.GreetingService;
import com.bobocode.service.GreetingServiceUA;
import lombok.SneakyThrows;

import java.util.Map;

public class Application {

    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext applicationContext = new PackageScanApplicationContext("com.bobocode");

        Map<String, GreetingService> allBeans = applicationContext.getAllBeans(GreetingService.class);
        allBeans.forEach((beanName, bean) -> System.out.println(beanName + " : " + bean.sayHello()));

        GreetingServiceUA uaGreeting = applicationContext.getBean("uaGreeting", GreetingServiceUA.class);
        System.out.println(uaGreeting.sayHello());

        AdditionalService additionalService = applicationContext.getBean(AdditionalService.class);
        System.out.println(additionalService.getGreetingServiceUA().sayHello());

    }
}
