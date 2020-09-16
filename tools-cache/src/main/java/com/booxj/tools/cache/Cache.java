package com.booxj.tools.cache;

import com.booxj.tools.cache.impl.CacheObj;

import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Function;

public interface Cache<K, V> extends Iterable<V>, Serializable {

    int capacity();

    long timeout();

    void put(K key, V value);

    void put(K key, V object, long timeout);

    V get(K key);

    /**
     * 从缓存中获得对象，当对象不在缓存中或已经过期返回Func0回调产生的对象
     *
     * @param key 键
     * @param function 如果不存在回调方法，用于生产值对象
     * @return 值对象
     */
     V get(K key, Function<K,V> function);

    /**
     * 从缓存中获得对象，当对象不在缓存中或已经过期返回<code>null</code>
     * <p>
     * 调用此方法时，会检查上次调用时间，如果与当前时间差值大于超时时间返回<code>null</code>，否则返回值。
     *
     * @param key 键
     * @param isUpdateLastAccess 是否更新最后访问时间，即重新计算超时时间。
     * @return 键对应的对象
     */
    V get(K key, boolean isUpdateLastAccess);

    Iterator<CacheObj<K, V>> cacheObjIterator();

    int prune();

    boolean isFull();

    void remove(K key);

    void clear();

    int size();

    boolean isEmpty();

    boolean containsKey(K key);
}
