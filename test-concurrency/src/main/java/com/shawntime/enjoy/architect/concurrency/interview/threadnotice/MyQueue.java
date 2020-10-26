package com.shawntime.enjoy.architect.concurrency.interview.threadnotice;

import java.util.ArrayList;
import java.util.List;

public class MyQueue {

    private List<Integer> list = new ArrayList<>();

    public void add(int id) {
        list.add(id);
    }

    public int size() {
        return list.size();
    }
}
