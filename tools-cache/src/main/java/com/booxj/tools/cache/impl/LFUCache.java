package com.booxj.tools.cache.impl;

import java.util.HashMap;
import java.util.Iterator;

/**
 * LFU(least frequently used) 最少使用率缓存<br>
 * 根据使用次数来判定对象是否被持续缓存<br>
 * 使用率是通过访问次数计算的。<br>
 * 当缓存满时清理过期对象。<br>
 * 清理后依旧满的情况下清除最少访问（访问计数最小）的对象并将其他对象的访问数减去这个最小访问数，以便新对象进入后可以公平计数。
 *
 * @param <K>
 * @param <V>
 */
public class LFUCache<K, V> extends AbstractCache<K, V> {

    private static final long serialVersionUID = 1L;

    public LFUCache(int capacity) {
        this(capacity, 0);
    }

    public LFUCache(int capacity, long timeout) {
        if (Integer.MAX_VALUE == capacity) {
            capacity -= 1;
        }

        this.capacity = capacity;
        this.timeout = timeout;
        cacheMap = new HashMap<>(capacity + 1, 1.0f);
    }

    @Override
    protected int pruneCache() {
        int count = 0;
        CacheObj<K, V> comin = null;

        // 清理过期对象并找出访问最少的对象
        Iterator<CacheObj<K, V>> values = cacheMap.values().iterator();
        CacheObj<K, V> co;
        while (values.hasNext()) {
            co = values.next();
            if (co.isExpired() == true) {
                remove(co.key);
                count++;
                continue;
            }

            //找出访问最少的对象
            if (comin == null || co.accessCount < comin.accessCount) {
                comin = co;
            }
        }

        // 减少所有对象访问量，并清除减少后为0的访问对象
        if (isFull() && comin != null) {
            long minAccessCount = comin.accessCount;

            values = cacheMap.values().iterator();
            CacheObj<K, V> co1;
            while (values.hasNext()) {
                co1 = values.next();
                co1.accessCount -= minAccessCount;
                if (co1.accessCount <= 0) {
                    remove(co1.key);
                    count++;
                }
            }
        }

        return count;
    }
}
