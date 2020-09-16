package com.booxj.tools.cache.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CacheObjIterator<K, V> implements Iterator<CacheObj<K, V>>, Serializable {

    private static final long serialVersionUID = 1L;

    private final Iterator<CacheObj<K, V>> iterator;
    private CacheObj<K, V> nextValue;

    CacheObjIterator(Iterator<CacheObj<K, V>> iterator) {
        this.iterator = iterator;
        nextValue();
    }

    @Override
    public boolean hasNext() {
        return nextValue != null;
    }

    @Override
    public CacheObj<K, V> next() {
        if (false == hasNext()) {
            throw new NoSuchElementException();
        }
        final CacheObj<K, V> cachedObject = nextValue;
        nextValue();
        return cachedObject;
    }

    private void nextValue() {
        while (iterator.hasNext()) {
            nextValue = iterator.next();
            if (nextValue.isExpired() == false) {
                return;
            }
        }
        nextValue = null;
    }
}
