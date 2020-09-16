package com.booxj.tools.cache.impl;

import com.booxj.tools.cache.Cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Function;

public abstract class AbstractCache<K, V> implements Cache<K, V> {

    private static final long serialVersionUID = 1L;
    protected Map<K, CacheObj<K, V>> cacheMap;
    private final StampedLock lock = new StampedLock();

    protected int capacity;
    /**
     * 全局缓存失效时长， 小于等于0 表示无限制，单位毫秒
     */
    protected long timeout;
    /**
     * 命中数
     */
    protected int hitCount;
    /**
     * 丢失数
     */
    protected int missCount;

    @Override
    public void put(K key, V object) {
        put(key, object, timeout);
    }

    @Override
    public void put(K key, V object, long timeout) {
        final long stamp = lock.writeLock();
        try {
            putWithoutLock(key, object, timeout);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    private void putWithoutLock(K key, V object, long timeout) {
        CacheObj<K, V> co = new CacheObj<>(key, object, timeout);
        if (isFull()) {
            pruneCache();
        }
        cacheMap.put(key, co);
    }

    @Override
    public boolean containsKey(K key) {
        final long stamp = lock.readLock();
        try {
            final CacheObj<K, V> co = cacheMap.get(key);
            // 不存在或已移除
            if (co == null) {
                return false;
            }
            if (false == co.isExpired()) {
                // 命中
                return true;
            } else {
                // 过期
                remove(key, true);
                return false;
            }
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getMissCount() {
        return missCount;
    }

    @Override
    public V get(K key) {
        return get(key, true);
    }

    @Override
    public V get(K key, Function<K, V> function) {
        V v = get(key);
        if (null == v && null != function) {
            final long stamp = lock.writeLock();
            try {
                // 双重检查锁
                final CacheObj<K, V> co = cacheMap.get(key);
                if (null == co || co.isExpired()) {
                    try {
                        v = function.apply(key);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    putWithoutLock(key, v, this.timeout);
                } else {
                    v = co.get(true);
                }
            } finally {
                lock.unlockWrite(stamp);
            }
        }
        return v;
    }

    @Override
    public V get(K key, boolean isUpdateLastAccess) {
        // 尝试读取缓存，使用乐观读锁
        long stamp = lock.readLock();
        try {
            // 不存在或已移除
            final CacheObj<K, V> co = cacheMap.get(key);
            if (null == co) {
                missCount++;
                return null;
            }
            if (co.isExpired()) {
                // 过期
                missCount++;
                remove(key, true);
                return null;
            } else {
                // 命中
                hitCount++;
                return co.get(isUpdateLastAccess);
            }
        } finally {
            lock.unlock(stamp);
        }
    }

    @Override
    public Iterator<V> iterator() {
        CacheObjIterator<K, V> copiedIterator = (CacheObjIterator<K, V>) this.cacheObjIterator();
        return new CacheValuesIterator<>(copiedIterator);
    }

    @Override
    public Iterator<CacheObj<K, V>> cacheObjIterator() {
        final long stamp = lock.readLock();
        try {
            return new CacheObjIterator(this.cacheMap.values().iterator());
        } finally {
            lock.unlockRead(stamp);
        }
    }

    protected abstract int pruneCache();

    @Override
    public final int prune() {
        final long stamp = lock.writeLock();
        try {
            return pruneCache();
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public long timeout() {
        return timeout;
    }

    @Override
    public boolean isFull() {
        return (capacity > 0) && (cacheMap.size() >= capacity);
    }

    @Override
    public void remove(K key) {
        remove(key, false);
    }

    @Override
    public void clear() {
        final long stamp = lock.writeLock();
        try {
            cacheMap.clear();
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public int size() {
        return cacheMap.size();
    }

    @Override
    public boolean isEmpty() {
        return cacheMap.isEmpty();
    }

    @Override
    public String toString() {
        return this.cacheMap.toString();
    }

    /**
     * 对象移除回调。默认无动作
     *
     * @param key          键
     * @param cachedObject 被缓存的对象
     */
    protected void onRemove(K key, V cachedObject) {
        // ignore
    }

    private void remove(K key, boolean withMissCount) {
        CacheObj<K, V> co = cacheMap.remove(key);
        if (withMissCount) {
            // 在丢失计数有效的情况下，移除一般为get时的超时操作，此处应该丢失数+1
            this.missCount++;
        }

        if (null != co) {
            onRemove(co.key, co.obj);
        }
    }

    private CacheObj<K, V> removeWithoutLock(K key, boolean withMissCount) {
        final CacheObj<K, V> co = cacheMap.remove(key);
        if (withMissCount) {
            // 在丢失计数有效的情况下，移除一般为get时的超时操作，此处应该丢失数+1
            this.missCount++;
        }
        return co;
    }
}
