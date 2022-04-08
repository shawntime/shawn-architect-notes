package com.shawntime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.shawntime.api"})
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
public class TestSpringCloudProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringCloudProductApplication.class, args);
    }

}
