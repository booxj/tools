package com.booxj.tools.cache;

import com.booxj.tools.cache.impl.FIFOCache;
import com.booxj.tools.cache.impl.LFUCache;
import com.booxj.tools.cache.impl.LRUCache;
import com.booxj.tools.cache.impl.TimedCache;

public class CacheFactory {


    public static <K, V> FIFOCache<K, V> newFIFOCache(int capacity) {
        return new FIFOCache<>(capacity);
    }

    public static <K, V> FIFOCache<K, V> newFIFOCache(int capacity, long timeout) {
        return new FIFOCache<>(capacity, timeout);
    }

    public static <K, V> LFUCache<K, V> newLFUCache(int capacity) {
        return new LFUCache<>(capacity);
    }

    public static <K, V> LFUCache<K, V> newLFUCache(int capacity, long timeout) {
        return new LFUCache<>(capacity, timeout);
    }

    public static <K, V> LRUCache<K, V> newLRUCache(int capacity) {
        return new LRUCache<>(capacity);
    }

    public static <K, V> LRUCache<K, V> newLRUCache(int capacity, long timeout) {
        return new LRUCache<>(capacity, timeout);
    }

    public static <K, V> TimedCache<K, V> newTimedCache(){
        return new TimedCache<>();
    }

    public static <K, V> TimedCache<K, V> newTimedCache(long timeout){
        return new TimedCache<>(timeout);
    }
}
