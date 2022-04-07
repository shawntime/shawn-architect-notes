package com.shawntime.api.user.model;

/**
 * @author mashaohua
 * @date 2022/3/30 17:11
 */
public class MallUserOut {

    private int userId;

    private int username;

    private int age;

    private int sex;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
