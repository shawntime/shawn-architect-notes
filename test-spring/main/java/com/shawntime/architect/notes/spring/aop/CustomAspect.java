package com.shawntime.architect.notes.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomAspect {

    @Pointcut("execution(public com.shawntime.architect.notes.spring.entity.User com.shawntime.architect.notes.spring.service.*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object log(ProceedingJoinPoint pjp) {
        System.out.println("start...");
        Object object = null;
        try {
            object = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            System.out.println("end...");
        }
        return object;
    }
}
