package com.shawntime.architect.notes.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms100M -Xmx100M -XX:+PrintGCDetails
 */
public class HeapTest {

    private byte[] bytes = new byte[1024 * 100];

    public static void main(String[] args) throws InterruptedException {
        List<HeapTest> dataList = new ArrayList<>();
        for (;;) {
            dataList.add(new HeapTest());
            Thread.sleep(30);
        }
    }
}
