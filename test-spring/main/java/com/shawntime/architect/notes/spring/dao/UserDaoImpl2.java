package com.shawntime.architect.notes.spring.dao;

import com.shawntime.architect.notes.spring.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class UserDaoImpl2 implements IUserDao {

    @Override
    public User getUser(int id) {
        User user = new User();
        user.setUserId(2);
        user.setUserName("李四");
        user.setAge(40);

        return user;
    }

    @Override
    public int save(User user) {
        return 0;
    }

    @Override
    public User save(String userName, int age) {
        User user = new User();
        user.setUserId(10);
        user.setUserName(userName);
        user.setAge(age);
        return user;
    }
}
