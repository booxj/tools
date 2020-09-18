package com.booxj.tools.core.collection;

import java.util.*;

/**
 * 集合工具类,封装了{@link Iterable},{@link List},{@link Set},{@link Map} 的相关方法
 */
public class CollectionUtil {

    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        return null == iterable || iterable.iterator().hasNext();
    }

    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    public static <E> boolean isNotEmpty(Collection<E> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Iterable<?> iterable) {
        return !isEmpty(iterable);
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    // ----------------------------------------------------------------------------------------------- List

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> List<T> newArrayList(T... ts) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, ts);
        return list;
    }

    public static <T> List<T> newLinkedList() {
        return new LinkedList<>();
    }

    public static <T> List<T> newLinkedList(T... ts) {
        List<T> list = new LinkedList<>();
        Collections.addAll(list, ts);
        return list;
    }
    // ----------------------------------------------------------------------------------------------- Map

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K, V> HashMap<K, V> newHashMap(int size) {
        return new HashMap<>(size);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int size) {
        return new LinkedHashMap<>(size);
    }

    // ----------------------------------------------------------------------------------------------- Set

    public static <T> HashSet<T> newHashSet() {
        return new HashSet<>();
    }

    public static <T> HashSet<T> newHashSet(T... ts) {
        final HashSet<T> set = new HashSet<>();
        Collections.addAll(set, ts);
        return set;
    }

    public static <T> HashSet<T> newHashSet(Collection<T> collection) {
        return new HashSet<>(collection);
    }

    public static <T> LinkedHashSet<T> newLinkedHashSet() {
        return new LinkedHashSet<>();
    }

    public static <T> LinkedHashSet<T> newLinkedHashSet(T... ts) {
        final LinkedHashSet<T> set = new LinkedHashSet<>();
        Collections.addAll(set, ts);
        return set;
    }

    public static <T> HashSet<T> newLinkedHashSet(Collection<T> collection) {
        return new LinkedHashSet<>(collection);
    }
}
