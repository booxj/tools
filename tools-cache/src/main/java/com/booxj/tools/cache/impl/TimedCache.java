package com.booxj.tools.cache.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 定时缓存<br>
 * 此缓存没有容量限制，对象只有在过期后才会被移除
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public class TimedCache<K, V> extends AbstractCache<K, V> {

    private static final long serialVersionUID = 1L;
    private final AtomicInteger timeCacheNumber = new AtomicInteger(1);

    private ScheduledFuture<?> pruneJobFuture;

    public TimedCache() {
        this(0, new HashMap<>());
    }

    public TimedCache(long timeout) {
        this(timeout, new HashMap<>());
    }

    public TimedCache(long timeout, Map<K, CacheObj<K, V>> map) {
        this.capacity = 0;
        this.timeout = timeout;
        this.cacheMap = map;
        this.schedulePrune(1000);
    }

    public TimedCache(long timeout, Map<K, CacheObj<K, V>> map, long pruneDelay) {
        this.capacity = 0;
        this.timeout = timeout;
        this.cacheMap = map;
        this.schedulePrune(pruneDelay);
    }

    @Override
    protected int pruneCache() {
        int count = 0;
        Iterator<CacheObj<K, V>> values = cacheMap.values().iterator();
        CacheObj<K, V> co;
        while (values.hasNext()) {
            co = values.next();
            if (co.isExpired()) {
                remove(co.key);
                count++;
            }
        }
        return count;
    }

    /**
     * 定时清理
     *
     * @param delay 间隔时长，单位毫秒
     */
    public void schedulePrune(long delay) {
        this.pruneJobFuture = new ScheduledThreadPoolExecutor(1, r -> new Thread(r, "TimedCache-schedulePrune-" + timeCacheNumber))
                .scheduleAtFixedRate(this::prune, delay, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 取消定时清理
     */
    public void cancelPruneSchedule() {
        if (null != pruneJobFuture) {
            pruneJobFuture.cancel(true);
        }
    }
}
