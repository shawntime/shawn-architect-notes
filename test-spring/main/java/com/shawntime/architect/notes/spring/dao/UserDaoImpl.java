package com.shawntime.architect.notes.spring.dao;

import com.shawntime.architect.notes.spring.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("online")
public class UserDaoImpl implements IUserDao {

    @Override
    public User getUser(int id) {
        User user = new User();
        user.setUserId(1);
        user.setUserName("张三");
        user.setAge(20);

        return user;
    }

    @Override
    public int save(User user) {
        return 1;
    }

    @Override
    public User save(String userName, int age) {
        User user = new User();
        user.setUserId(1);
        user.setUserName(userName);
        user.setAge(age);
        return user;
    }
}
