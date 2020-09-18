package com.booxj.tools.core.lang;

import com.booxj.tools.core.collection.CollectionUtil;
import com.booxj.tools.core.utils.ArrayUtil;
import com.booxj.tools.core.utils.StringUtil;

import java.util.Map;

public class Assert {

    public static void isTrue(boolean expression) throws IllegalArgumentException {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static void isTrue(boolean expression, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
        if (!expression) {
            throw new IllegalArgumentException(StringUtil.format(errorMsgTemplate, params));
        }
    }

    public static void isFalse(boolean expression) throws IllegalArgumentException {
        isFalse(expression, "[Assertion failed] - this expression must be false");
    }

    public static void isFalse(boolean expression, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
        if (expression) {
            throw new IllegalArgumentException(StringUtil.format(errorMsgTemplate, params));
        }
    }

    public static void isNull(Object object) throws IllegalArgumentException {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static void isNull(Object object, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
        if (object != null) {
            throw new IllegalArgumentException(StringUtil.format(errorMsgTemplate, params));
        }
    }

    public static void notNull(Object object) throws IllegalArgumentException {
        notNull(object, "[Assertion failed] - the object argument must not be null");
    }

    public static <T> T notNull(T object, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
        if (object == null) {
            throw new IllegalArgumentException(StringUtil.format(errorMsgTemplate, params));
        }
        return object;
    }

    public static void notHasNull(Object... objects) throws IllegalArgumentException {
        notEmpty(objects);
        for (Object object : objects) {
            notNull(object, "[Assertion failed] - the object argument must not be null");
        }
    }

    public static <T extends CharSequence> T notBlank(T text, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
        if (StringUtil.isBlank(text)) {
            throw new IllegalArgumentException(StringUtil.format(errorMsgTemplate, params));
        }
        return text;
    }

    public static <T extends CharSequence> T notBlank(T text) throws IllegalArgumentException {
        return notBlank(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    public static Object[] notEmpty(Object[] array, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
        if (ArrayUtil.isEmpty(array)) {
            throw new IllegalArgumentException(StringUtil.format(errorMsgTemplate, params));
        }
        return array;
    }

    public static Object[] notEmpty(Object[] array) throws IllegalArgumentException {
        return notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    public static <K, V> Map<K, V> notEmpty(Map<K, V> map, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
        if (CollectionUtil.isEmpty(map)) {
            throw new IllegalArgumentException(StringUtil.format(errorMsgTemplate, params));
        }
        return map;
    }

    public static <K, V> Map<K, V> notEmpty(Map<K, V> map) throws IllegalArgumentException {
        return notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }


}
