package com.shawntime.architect.notes.proto.config;

import java.util.concurrent.CountDownLatch;

import com.shawntime.architect.notes.proto.JsonHelper;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @author mashaohua
 * @date 2021/2/1 16:30
 */
public class ConfigHandler<T> implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {

    private ZooKeeper zooKeeper;

    private String path;

    private Object ctx;

    private T data;

    private Class<T> clazz;

    private CountDownLatch countDownLatch;

    public ConfigHandler(ZooKeeper zooKeeper,
                         String path,
                         Object ctx,
                         Class<T> clazz) {
        this.zooKeeper = zooKeeper;
        this.path = path;
        this.ctx = ctx;
        this.clazz = clazz;
    }

    public void await() {
        countDownLatch = new CountDownLatch(1);
        zooKeeper.exists(path, this, this, ctx);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public T getData() {
        if (data != null) {
            return data;
        }
        await();
        return data;
    }

    public void loadData() {
        zooKeeper.getData(path, this, this, ctx);
        System.out.println(Thread.currentThread().getName());
    }

    /**
     * 节点监听
     */
    @Override
    public void process(WatchedEvent event) {
        String path = event.getPath();
        System.out.println(path);
        Event.EventType eventType = event.getType();
        switch (eventType) {
            case None:
                break;
            case NodeCreated:
                // 节点被创建时获取配置数据
                System.out.println("节点创建成功");
                loadData();
                break;
            case NodeDeleted:
                // 节点被删除，继续等待
                System.out.println("节点被删除");
                data = null;
                break;
            case NodeDataChanged:
                // 节点数据被修改时重新加载配置数据
                System.out.println("节点数据修改");
                loadData();
                break;
            case NodeChildrenChanged:
                break;
        }
    }

    /**
     * 判断节点是否存在结果回调
     */
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if (stat != null) {
            // 节点存在
            loadData();
        }
    }

    /**
     * 查询节点数据回调
     */
    @Override
    public void processResult(int rc, String path, Object ctx, byte[] bytes, Stat stat) {
        if (bytes != null) {
            System.out.println(Thread.currentThread().getName());
            String json = new String(bytes);
            data = JsonHelper.deSerialize(json, clazz);
            countDownLatch.countDown();
        }
    }
}
