package com.shawntime.architect.notes.spring.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.shawntime.architect.notes.spring.annotation.MyAnnotation;
import com.shawntime.architect.notes.spring.dao.IUserDao;
import com.shawntime.architect.notes.spring.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author mashaohua
 * @title: TODO 要求能简洁的表达出类的功能
 * @description: TODO 简要描述类的职责、实现方式、作用功能
 * @date 2021/2/7 23:18
 * @menu
 */
@Service
public class UserServiceImpl implements IUserService, ApplicationContextAware {

    @Autowired
    private IUserDao userDao;

    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        System.out.println("初始化前置");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("资源释放");
    }

    @MyAnnotation
    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public int save(User user) {
        return userDao.save(user);
    }

    @Override
    public User save(String userName, int age) {
        return userDao.save(userName, age);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
