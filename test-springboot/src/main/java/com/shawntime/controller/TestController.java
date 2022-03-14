package com.shawntime.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mashaohua
 * @date 2022/3/14 16:38
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${server.port}")
    private int port;

    @RequestMapping("/port")
    public String getPort() {
        return String.valueOf(port);
    }
}
