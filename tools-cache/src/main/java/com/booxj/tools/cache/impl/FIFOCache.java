package com.booxj.tools.cache.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class FIFOCache<K, V> extends AbstractCache<K, V> {

    public FIFOCache(int capacity) {
        this(capacity, 0);
    }

    public FIFOCache(int capacity, long timeout) {
        this.capacity = capacity;
        this.timeout = timeout;
        cacheMap = new LinkedHashMap<>(capacity, 1.0f, false);
    }

    /**
     * 先进先出的清理策略<br>
     * 先遍历缓存清理过期的缓存对象，如果清理后还是满的，则删除第一个缓存对象
     */
    @Override
    protected int pruneCache() {
        int count = 0;
        CacheObj<K, V> first = null;

        // 清理过期对象并找出链表头部元素（先入元素）
        Iterator<CacheObj<K, V>> values = cacheMap.values().iterator();
        while (values.hasNext()) {
            CacheObj<K, V> co = values.next();
            if (co.isExpired()) {
                remove(co.key);
                count++;
            }
            if (first == null) {
                first = co;
            }
        }

        // 清理结束后依旧是满的，则删除第一个被缓存的对象
        if (isFull() && null != first) {
            remove(first.key);
            count++;
        }
        return count;
    }
}
