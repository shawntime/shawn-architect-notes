package com.shawntime.architect.notes.api;

import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mashaohua
 * @date 2022/2/6 9:58
 */
public class ZookeeperClientTest {

    private IZkClient zkClient;

    @Before
    public void init() {
        ZkConfigs zkConfigs = new ZkConfigs();
        zkConfigs.addZkConfig(new ZkConfig("192.168.73.133", 2181));
        String rootPath = "/api";
        zkClient = new ZkClient(zkConfigs, rootPath);
        zkClient.createNoExist(rootPath, null, CreateMode.PERSISTENT);
    }

    @Test
    public void test_async_create_persistent() throws InterruptedException {
        zkClient.create("/api/node-3", "我是一个持久化节点".getBytes(), CreateMode.PERSISTENT, "需要回调");
        TimeUnit.SECONDS.sleep(1000);
    }

    @Test
    public void test_async_create_e() throws InterruptedException {
        AsyncCallback.StringCallback stringCallback = (rc, path, ctx, name) -> {
            System.out.println("rc : " + rc);
            System.out.println("path : " + path);
            System.out.println("ctx : " + ctx);
            System.out.println("name : " + name);
        };
        zkClient.create("/api/node-4",
                "我是一个临时节点".getBytes(),
                CreateMode.EPHEMERAL, "需要回调",
                stringCallback);
        TimeUnit.SECONDS.sleep(1000);
    }

    @Test
    public void test_sync_create_persistent() {
        String path = zkClient.create("/api/node-2", "我是第一个持久化节点".getBytes(), CreateMode.PERSISTENT);
        Assert.assertEquals("/api/node-2", path);
    }

    @Test
    public void tet_sync_setData() {
        Stat stat = new Stat();
        String data = zkClient.getData("/api/node-2", null, stat);
        System.out.println(data);
        System.out.println(stat);
    }
}
