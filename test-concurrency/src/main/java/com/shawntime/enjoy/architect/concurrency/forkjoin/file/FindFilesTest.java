package com.shawntime.enjoy.architect.concurrency.forkjoin.file;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class FindFilesTest {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        File file = new File("D:/视频教程");
        FindFileTask findFiles = new FindFileTask(file);
        // 异步提交
        forkJoinPool.execute(findFiles);

        int result = 0;
        for (int i = 0; i < 1000; ++i) {
            result += i;
        }

        System.out.println("main result : " + result);

        findFiles.join(); // 阻塞

        System.out.println("end....");
    }
}
