package com.shawntime.architect.notes.spring.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(
        basePackages = "com.shawntime.architect.notes.spring")
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class Config {
}
