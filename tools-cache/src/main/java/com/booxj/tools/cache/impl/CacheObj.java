package com.booxj.tools.cache.impl;

import java.io.Serializable;

public class CacheObj<K, V> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final K key;
    protected final V obj;

    /**
     * 上次访问时间
     */
    private long lastAccess;
    /**
     * 访问次数
     */
    protected long accessCount;
    /**
     * 对象存活时长，小于等于0表示永久存活
     */
    private final long ttl;

    protected CacheObj(K key, V obj, long ttl) {
        this.key = key;
        this.obj = obj;
        this.ttl = ttl;
        this.lastAccess = System.currentTimeMillis();
    }

    boolean isExpired() {
        if (this.ttl > 0) {
            final long expiredTime = this.lastAccess + this.ttl;
            // expiredTime > 0 杜绝Long类型溢出变负数问题，当当前时间超过过期时间，表示过期
            return expiredTime > 0 && expiredTime < System.currentTimeMillis();
        }
        return false;
    }

    V get(boolean isUpdateLastAccess) {
        if (isUpdateLastAccess) {
            lastAccess = System.currentTimeMillis();
        }
        accessCount++;
        return this.obj;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.obj;
    }

    @Override
    public String toString() {
        return "cache [key=" + key + ", obj=" + obj + ", lastAccess=" + lastAccess + ", accessCount=" + accessCount + ", ttl=" + ttl + "]";
    }
}
