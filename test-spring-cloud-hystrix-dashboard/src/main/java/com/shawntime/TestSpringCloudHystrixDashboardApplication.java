package com.shawntime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableCircuitBreaker
@EnableHystrixDashboard
@SpringBootApplication
public class TestSpringCloudHystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringCloudHystrixDashboardApplication.class, args);
    }

}
