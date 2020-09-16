package com.booxj.tools.cache.impl;

import java.io.Serializable;
import java.util.Iterator;

public class CacheValuesIterator <V> implements Iterator<V>, Serializable {
    private static final long serialVersionUID = 1L;

    private final CacheObjIterator<?, V> cacheObjIter;

    CacheValuesIterator(CacheObjIterator<?, V> iterator) {
        this.cacheObjIter = iterator;
    }

    @Override
    public boolean hasNext() {
        return this.cacheObjIter.hasNext();
    }

    @Override
    public V next() {
        return cacheObjIter.next().getValue();
    }

}
