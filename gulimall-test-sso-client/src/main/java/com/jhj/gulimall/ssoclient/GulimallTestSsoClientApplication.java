package com.jhj.gulimall.ssoclient;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class GulimallTestSsoClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallTestSsoClientApplication.class, args);
    }

}
