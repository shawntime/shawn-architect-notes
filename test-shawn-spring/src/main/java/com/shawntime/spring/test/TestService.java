package com.shawntime.spring.test;

import com.shawntime.myspring.v1.annotation.Service;

@Service
public class TestService implements ITestService {

    @Override
    public int add(int i, int j) {
        return i + j;
    }
}
