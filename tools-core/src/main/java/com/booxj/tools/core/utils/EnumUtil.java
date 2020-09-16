package com.booxj.tools.core.utils;

import com.booxj.tools.core.lang.Assert;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Objects;

public class EnumUtil {

    /**
     * 指定类是否为Enum类
     *
     * @param clazz 类
     * @return 是否为Enum类
     */
    public static boolean isEnum(Class<?> clazz) {
        Assert.notNull(clazz);
        return clazz.isEnum();
    }

    /**
     * 获取枚举字符串值和枚举对象的Map对应，使用LinkedHashMap保证有序<br>
     * 结果中键为枚举名，值为枚举对象
     *
     * @param <E>       枚举类型
     * @param enumClass 枚举类
     * @return 枚举字符串值和枚举对象的Map对应，使用LinkedHashMap保证有序
     */
    public static <E extends Enum<E>> LinkedHashMap<String, E> getEnumMap(final Class<E> enumClass) {
        final LinkedHashMap<String, E> map = new LinkedHashMap<>();
        for (final E e : enumClass.getEnumConstants()) {
            map.put(e.name(), e);
        }
        return map;
    }

    /**
     * 判断某个值是存在枚举中
     *
     * @param <E>       枚举类型
     * @param enumClass 枚举类
     * @param name      需要查找的值
     * @return 是否存在
     */
    public static <E extends Enum<E>> boolean contains(final Class<E> enumClass, String name) {
        EnumSet<E> set = EnumSet.allOf(enumClass);
        for (E e : set) {
            if (Objects.equals(e.name(), name)) {
                return true;
            }
        }
        return false;
    }
}
