package com.avst.accredit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
public class AccreditApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccreditApplication.class, args);
    }

}
