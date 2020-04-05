package com.shawntime.enjoy.architect.concurrency.forkjoin.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * 异步的方式不带返回值
 */
public class FindFileTask extends RecursiveAction {

    private File file;

    public FindFileTask(File file) {
        this.file = file;
    }

    @Override
    protected void compute() {
        if (file == null) {
            return;
        }
        File[] files = file.listFiles();
        List<FindFileTask> tasks = new ArrayList<>();
        for (File subFile : files) {
            if (subFile.isDirectory()) {
                FindFileTask task = new FindFileTask(subFile);
                tasks.add(task);
            } else {
                if (subFile.getName().endsWith("mp4")) {
                    System.out.println(subFile.getAbsolutePath());
                }
            }
        }
        if (tasks.isEmpty()) {
            return;
        }
        for (FindFileTask task : invokeAll(tasks)) {
            task.join();
        }
    }
}
