package com.shawntime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TestEurekaClientOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestEurekaClientOrderApplication.class, args);
    }

}
