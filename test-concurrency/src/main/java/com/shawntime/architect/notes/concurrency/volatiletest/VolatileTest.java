package com.shawntime.architect.notes.concurrency.volatiletest;

public class VolatileTest {

    private volatile boolean status;

    public static void main(String[] args) {
        VolatileTest test = new VolatileTest();
        test.status = true;

    }

}
