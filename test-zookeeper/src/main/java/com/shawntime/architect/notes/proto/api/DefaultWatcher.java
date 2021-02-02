package com.shawntime.architect.notes.proto.api;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author mashaohua
 * @date 2021/2/1 13:52
 */
public class DefaultWatcher implements Watcher {

    private CountDownLatch countDownLatch;

    public DefaultWatcher(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        Event.KeeperState state = watchedEvent.getState();
        switch (state) {
            case Unknown:
                break;
            case Disconnected:
                System.out.println("链接断开...");
                countDownLatch = new CountDownLatch(1);
                break;
            case NoSyncConnected:
                break;
            case SyncConnected:
                System.out.println("链接成功...");
                countDownLatch.countDown();
                break;
            case AuthFailed:
                break;
            case ConnectedReadOnly:
                break;
            case SaslAuthenticated:
                break;
            case Expired:
                break;
        }
    }
}
