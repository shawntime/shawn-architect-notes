package com.shawntime.architect.notes.spring;

import com.shawntime.architect.notes.spring.config.Config;
import com.shawntime.architect.notes.spring.entity.User;
import com.shawntime.architect.notes.spring.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AopTest {

    private AnnotationConfigApplicationContext context;

    @Before
    public void before() {
        context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("test");
        context.register(Config.class);
        context.refresh();
    }

    @Test
    public void test_start() {
        IUserService userService = context.getBean(IUserService.class);
        User user = userService.getUser(1);
        System.out.println(user);
    }
}
