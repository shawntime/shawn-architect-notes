package com.shawntime.architect.notes.api;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

/**
 * @author mashaohua
 * @date 2022/2/17 13:48
 */
public interface IZkClient {

    String create(String path, byte[] data, CreateMode createMode);

    String createNoExist(String path, byte[] data, CreateMode createMode);

    void create(String path, byte[] data, CreateMode createMode, Object ctx);

    void create(String path, byte[] data, CreateMode createMode, Object ctx, AsyncCallback.StringCallback callback);

    Stat exist(String path);

    Stat setData(String path, byte[] data, int version);

    void setData(String path, byte[] data, int version, AsyncCallback.StatCallback callback, Object ctx);

    String getData(String path, Watcher watcher, Stat stat);

}
