package com.shawntime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TestEurekaServer9101Application {

    public static void main(String[] args) {
        SpringApplication.run(TestEurekaServer9101Application.class, args);
    }

}

