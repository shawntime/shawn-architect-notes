package com.shawntime.architect.notes.proto.api;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import com.shawntime.architect.notes.proto.config.ZKConfig;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author mashaohua
 * @date 2021/2/1 15:08
 */
public class ZKUtils {

    private static ZooKeeper zooKeeper;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static Watcher watcher;

    public static ZooKeeper getZooKeeper(List<ZKConfig> zkConfigs,
                                         String parentPath) {
        String connectString = zkConfigs.stream()
                .map(config -> config.getIp() + ":" + config.getPort())
                .collect(Collectors.joining(",")) + parentPath;
        int sessionTimeout = 10 * 60 * 1000;
        watcher = new DefaultWatcher(countDownLatch);
        try {
            // 异步的
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, watcher);
            countDownLatch.await();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }

    public static void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
