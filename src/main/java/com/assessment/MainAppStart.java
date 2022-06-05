package com.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MainAppStart {
    public static void main(String[] args) {
        SpringApplication.run(MainAppStart.class, args);
    }
}
