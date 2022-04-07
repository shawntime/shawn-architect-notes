package com.shawntime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.shawntime.api"})
@EnableEurekaClient
@SpringBootApplication
public class TestEurekaClientUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestEurekaClientUserApplication.class, args);
    }

}
