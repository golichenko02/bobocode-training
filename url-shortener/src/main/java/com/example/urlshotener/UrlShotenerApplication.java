package com.example.urlshotener;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@EnableCaching
public class UrlShotenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShotenerApplication.class, args);
    }

}
