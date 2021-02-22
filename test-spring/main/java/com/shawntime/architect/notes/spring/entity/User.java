package com.shawntime.architect.notes.spring.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {

    private int userId;

    private String userName;

    private int age;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userId", userId)
                .append("userName", userName)
                .append("age", age)
                .toString();
    }
}
