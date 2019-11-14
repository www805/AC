package com.avst.authorize;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.avst.authorize.web.dao")
public class AuthorizeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizeApplication.class, args);
    }

}
