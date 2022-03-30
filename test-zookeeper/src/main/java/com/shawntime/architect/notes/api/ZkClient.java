package com.shawntime.architect.notes.api;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @author mashaohua
 * @date 2022/2/17 11:21
 */
@Slf4j
public class ZkClient implements Watcher, IZkClient, AsyncCallback.StringCallback {

    private ZooKeeper zooKeeper;

    private ZkConfigs zkConfigs;

    private String rootPath;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public ZkClient(ZkConfigs zkConfigs, String rootPath) {
        this.zkConfigs = zkConfigs;
        this.rootPath = rootPath;
        init();
    }

    private void init() {
        String connectString = zkConfigs.getZkConnectString();
        int sessionTimeout = 30000;
        try {
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, this);
            countDownLatch.await();
        } catch (IOException e) {
            log.error("zk连接异常", e);
        } catch (InterruptedException e) {
            log.error("zk连接异常", e);
        }
    }

    @Override
    public void process(WatchedEvent event) {
        Event.KeeperState state = event.getState();
        switch (state) {
            case SyncConnected:
                System.out.println("Zookeeper连接成功...");
                createRootPath();
                countDownLatch.countDown();
                break;
        }
    }

    @Override
    public String create(String path, byte[] data, CreateMode createMode) {
        try {
            return zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
        } catch (KeeperException e) {
            log.error("创建节点异常, path:{}", path, e);
        } catch (InterruptedException e) {
            log.error("创建节点异常, path:{}", path, e);
        }
        return null;
    }

    @Override
    public String createNoExist(String path, byte[] data, CreateMode createMode) {
        if (exist(path) != null) {
            return path;
        }
        return create(path, data, createMode);
    }

    @Override
    public Stat exist(String path) {
        try {
            return zooKeeper.exists(path, false);
        } catch (KeeperException e) {
            log.error("查询节点异常, path:{}", path, e);
        } catch (InterruptedException e) {
            log.error("查询节点异常, path:{}", path, e);
        }
        return null;
    }

    @Override
    public Stat setData(String path, byte[] data, int version) {
        try {
            return zooKeeper.setData(path, data, version);
        } catch (KeeperException e) {
            log.error("修改节点异常, path:{}", path, e);
        } catch (InterruptedException e) {
            log.error("修改节点异常, path:{}", path, e);
        }
        return null;
    }

    @Override
    public void setData(String path, byte[] data, int version, StatCallback callback, Object ctx) {
        zooKeeper.setData(path, data, version, callback, ctx);
    }

    @Override
    public String getData(String path, Watcher watcher, Stat stat) {
        try {
            byte[] data = zooKeeper.getData(path, watcher, stat);
            if (data != null) {
                return new String(data);
            }
        } catch (KeeperException e) {
            log.error("查询节点异常, path:{}", path, e);
        } catch (InterruptedException e) {
            log.error("查询节点异常, path:{}", path, e);
        }
        return null;
    }

    @Override
    public void create(String path, byte[] data, CreateMode createMode, Object ctx) {
        zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode, this, ctx);
    }

    @Override
    public void create(String path, byte[] data, CreateMode createMode, Object ctx, StringCallback callback) {
        zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode, callback, ctx);
    }

    private void createRootPath() {
        if (exist(rootPath) != null) {
            return;
        }
        create(rootPath, null, CreateMode.PERSISTENT);
    }

    /**
     * 创建节点回调
     */
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        log.info("节点创建成功, path:{}, ctx:{}", path, ctx);
    }
}
