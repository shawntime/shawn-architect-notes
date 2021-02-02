package com.shawntime.architect.notes.proto.lock;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
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
 * @date 2021/2/2 10:22
 */
public class DistributedLock implements Lock, Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback, AsyncCallback.StatCallback {

    private ZooKeeper zooKeeper;

    private String childPath;

    private String threadName;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public DistributedLock(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    @Override
    public void lock() {
        threadName = Thread.currentThread().getName();
        System.out.println(threadName + "尝试获取lock....");
        Stat stat = new Stat();
        try {
            byte[] bytes = zooKeeper.getData("/", false, stat);
            if (bytes != null) {
                String data = new String(bytes);
                if (data.equals(threadName)) {
                    return;
                }
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        zooKeeper.create(
                "/lock",
                new byte[0],
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL,
                this,
                threadName);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(int time, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public void unLock() {
        try {
            System.out.println(threadName + " unlock ...");
            zooKeeper.setData("/", "".getBytes(), -1);
            zooKeeper.delete(childPath, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        Event.EventType type = event.getType();
        switch (type) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                // 节点被删除
                System.out.println("父节点被删除，重新查找");
                zooKeeper.getChildren("/", false, this, threadName);
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
        }
    }

    /**
     * 临时节点创建后回调
     */
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        if (StringUtils.isNotEmpty(name)) {
            System.out.println(">>>>>" + threadName + " : " + name);
            childPath = name;
            // 节点创建成功
            zooKeeper.getChildren("/", false, this, "查询子节点列表");
        } else {
            // 节点创建失败
            throw new RuntimeException("加锁异常");
        }
    }

    /**
     * 获取子节点列表回调
     */
    @Override
    public void processResult(int rc,
                              String path,
                              Object ctx,
                              List<String> children,
                              Stat stat) {
        if (children == null || children.size() == 0) {
            throw new RuntimeException("加锁失败");
        }
        Collections.sort(children);
        int index = children.indexOf(childPath.substring(1));
        if (index <= 0) {
            try {
                System.out.println(threadName + " get lock ....");
                zooKeeper.setData("/", threadName.getBytes(), -1);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        } else {
            String preChild = children.get(index - 1);
            System.out.println(threadName + " watch "+ preChild);
            zooKeeper.exists("/" + preChild, this, this, "判断上一个节点是否存在");
        }

    }

    /**
     * 节点是否存在回调
     */
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if (stat == null) {
            // 上一个节点被删除
            System.out.println(path + "节点已经不存在");
            zooKeeper.getChildren("/", false, this, "查询子节点列表");
        }
    }
}
