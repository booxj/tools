package com.booxj.tools.core.lang;

@FunctionalInterface
public interface Filter<T> {

    boolean accept(T t);
}
