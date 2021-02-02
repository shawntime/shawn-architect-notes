package com.shawntime.architect.notes.proto.lock;

import java.util.ArrayList;
import java.util.List;

import com.shawntime.architect.notes.proto.api.ZKUtils;
import com.shawntime.architect.notes.proto.config.ZKConfig;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mashaohua
 * @date 2021/2/2 12:53
 */
public class LockTest {

    private ZooKeeper zooKeeper;

    @Before
    public void connection() {
        List<ZKConfig> zkConfigs = new ArrayList<>();
        ZKConfig zkConfig1 = new ZKConfig();
        zkConfig1.setIp("127.0.0.1");
        zkConfig1.setPort(2181);
        zkConfigs.add(zkConfig1);

        ZKConfig zkConfig2 = new ZKConfig();
        zkConfig2.setIp("127.0.0.1");
        zkConfig2.setPort(2182);
        zkConfigs.add(zkConfig2);

        ZKConfig zkConfig3 = new ZKConfig();
        zkConfig3.setIp("127.0.0.1");
        zkConfig3.setPort(2183);
        zkConfigs.add(zkConfig3);

        ZKConfig zkConfig4 = new ZKConfig();
        zkConfig4.setIp("127.0.0.1");
        zkConfig4.setPort(2184);
        zkConfigs.add(zkConfig4);

        ZKConfig zkConfig5 = new ZKConfig();
        zkConfig5.setIp("127.0.0.1");
        zkConfig5.setPort(2185);
        zkConfigs.add(zkConfig5);

        String parentPath = "/shawntime";
        zooKeeper = ZKUtils.getZooKeeper(zkConfigs, parentPath);
    }

    @After
    public void close() {
        ZKUtils.close();
    }

    @Test
    public void test_lock() {
        for (int i = 0; i < 10; ++i) {
            new Thread(() -> {
                Lock lock = new DistributedLock(zooKeeper);
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "正在执行...");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unLock();
            }, "thread-" + (i +1)).start();
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
