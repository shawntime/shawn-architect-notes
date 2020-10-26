package com.shawntime.enjoy.architect.concurrency.cas;

import java.util.concurrent.atomic.AtomicReference;

public class TestAtomicReference {

    private static Bean unSafeBean = new Bean(1, 10, "zhangsan");

    private static AtomicReference<Bean> atomicReference = new AtomicReference(unSafeBean);

    public static void main(String[] args) {


    }

    private static class MyThread extends Thread {

    }

    private static class Bean {

        private int id;

        private int age;

        private String name;

        public Bean(int id, int age, String name) {
            this.id = id;
            this.age = age;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
