package com.shawntime.testspringcloudzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class TestSpringCloudZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringCloudZuulApplication.class, args);
    }

}
