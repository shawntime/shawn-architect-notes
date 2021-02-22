package com.shawntime.architect.notes.spring.service;

import com.shawntime.architect.notes.spring.entity.User;

public interface IUserService {

    User getUser(int id);

    int save(User user);

    User save(String userName, int age);
}
