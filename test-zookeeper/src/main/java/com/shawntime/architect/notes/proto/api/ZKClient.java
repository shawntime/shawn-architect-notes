package com.shawntime.architect.notes.proto.api;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import com.shawntime.architect.notes.proto.config.ZKConfig;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

/**
 * @author mashaohua
 * @date 2021/2/1 13:46
 */
public class ZKClient implements Watcher, AsyncCallback.StringCallback {

    private ZooKeeper zooKeeper;

    private CountDownLatch countDownLatch;

    private String parentPath;

    public ZKClient(List<ZKConfig> zkConfigs,
                    CountDownLatch countDownLatch,
                    String parentPath) {
        this.countDownLatch = countDownLatch;
        this.parentPath = parentPath;
        String connectString = zkConfigs.stream()
                .map(config -> config.getIp() + ":" + config.getPort())
                .collect(Collectors.joining(",")) + parentPath;
        int sessionTimeout = 3000;
        try {
            // 异步的
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, this);
            countDownLatch.await();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * String path,
     * byte[] data,
     * List<ACL> acl,
     * CreateMode createMode,
     * StringCallback cb,
     * Object ctx
     */
    public void createNode(String path,
                           byte[] data,
                           List<ACL> aclList,
                           CreateMode createMode,
                           Object ctx) {
        zooKeeper.create(path, data, aclList, createMode, this, ctx);
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    /**
     * 创建节点监听回调
     */
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {

    }

    /**
     * watcher监听回调
     */
    @Override
    public void process(WatchedEvent event) {
        Event.KeeperState state = event.getState();
        switch (state) {
            case SyncConnected:
                System.out.println("Zookeeper连接成功...");
                countDownLatch.countDown();
                break;
        }
    }
}
