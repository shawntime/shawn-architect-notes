package com.shawntime.architect.notes.spring.dao;

import com.shawntime.architect.notes.spring.entity.User;

public interface IUserDao {

    User getUser(int id);

    int save(User user);

    User save(String userName, int age);
}
