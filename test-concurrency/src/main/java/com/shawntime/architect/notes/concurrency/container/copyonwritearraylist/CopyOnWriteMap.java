package com.shawntime.architect.notes.concurrency.container.copyonwritearraylist;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写复制Map实现
 */
public class CopyOnWriteMap<K, V> implements Map<K, V>, Cloneable {

    private transient volatile Map<K, V> map;

    final transient ReentrantLock lock = new ReentrantLock();

    public CopyOnWriteMap() {
        map = new HashMap<>(16);
    }

    public CopyOnWriteMap(int size) {
        map = new HashMap<>(size);
    }

    public void setMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        lock.lock();
        try {
            Map<K, V> newMap = new HashMap<>();
            newMap.putAll(map);
            V oldValue = newMap.get(key);
            newMap.put(key, value);
            setMap(newMap);
            return oldValue;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        lock.lock();
        try {
            Map<K, V> newMap = new HashMap<>();
            newMap.putAll(map);
            newMap.putAll(m);
            setMap(newMap);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            Map<K, V> newMap = new HashMap<>();
            setMap(newMap);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public V remove(Object key) {
        lock.lock();
        try {
            Map<K, V> newMap = new HashMap<>();
            newMap.putAll(map);
            V value = newMap.get(key);
            if (value != null) {
                newMap.remove(key);
            }
            setMap(newMap);
            return value;
        } finally {
            lock.unlock();
        }
    }
}
