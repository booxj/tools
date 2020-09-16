package com.booxj.tools.core.utils;

import com.booxj.tools.core.collection.CollectionUtil;
import com.booxj.tools.core.lang.Assert;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassUtil {

    // --------------------------------------------------------------------------------------------------------- Class

    public static <T> Class<T> getClass(String className) throws ClassNotFoundException {
        return StringUtil.isBlank(className) ? null : (Class<T>) Class.forName(className);
    }

    public static <T> Class<T> getClass(T obj) {
        return ((null == obj) ? null : (Class<T>) obj.getClass());
    }

    // --------------------------------------------------------------------------------------------------------- ClassName

    public static String getClassName(Object obj, boolean isSimple) {
        if (null == obj) {
            return null;
        }
        final Class<?> clazz = obj.getClass();
        return getClassName(clazz, isSimple);
    }

    public static String getClassName(Class<?> clazz, boolean isSimple) {
        if (null == clazz) {
            return null;
        }
        return isSimple ? clazz.getSimpleName() : clazz.getName();
    }

    // --------------------------------------------------------------------------------------------------------- Constructor
    public static Constructor<?>[] getDeclaredConstructors(Class<?> clazz) {
        return clazz == null ? null : clazz.getDeclaredConstructors();
    }

    public static Constructor<?> getDeclaredConstructor(Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz == null ? null : clazz.getDeclaredConstructor(parameterTypes);
    }

    public static Constructor<?>[] getConstructors(Class<?> clazz) {
        return clazz == null ? null : clazz.getConstructors();
    }

    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz == null ? null : clazz.getConstructor(parameterTypes);
    }

    // ----------------------------------------------------------------------------------------- Method

    public static Set<String> getDeclaredMethodNames(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        Method[] methods = clazz.getDeclaredMethods();
        return ArrayUtil.isEmpty(methods) ?
                CollectionUtil.newHashSet() :
                Arrays.stream(methods).map(m -> m.getName()).collect(Collectors.toSet());
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return null == clazz ? null : clazz.getDeclaredMethods();
    }

    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) throws NoSuchMethodException {
        return clazz.getDeclaredMethod(methodName, paramTypes);
    }

    public static Method[] getMethods(Class<?> clazz) {
        return null == clazz ? null : clazz.getMethods();
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) throws NoSuchMethodException {
        return clazz.getMethod(methodName, paramTypes);
    }

    // ----------------------------------------------------------------------------------------- Field classes

    public static Field getField(Class<?> beanClass, String name) throws SecurityException {
        final Field[] fields = getFields(beanClass);
        if (ArrayUtil.isNotEmpty(fields)) {
            for (Field field : fields) {
                if ((name.equals(field.getName()))) {
                    return field;
                }
            }
        }
        return null;
    }

    public static Field[] getFields(Class<?> beanClass) throws SecurityException {
        Assert.notNull(beanClass);
        Field[] fields = beanClass.getFields();
        return fields;
    }

    public static Object getFieldValue(Object obj, String fieldName) throws IllegalAccessException {
        if (StringUtil.isBlank(fieldName)) {
            return null;
        }
        if (obj instanceof Class) {
            obj = null;
        }
        Field field = getField(obj.getClass(), fieldName);
        setAccessible(field);
        return field.get(obj);
    }

    public static Object getFieldValue(Object obj, Field field) throws IllegalAccessException {
        if (null == field) {
            return null;
        }
        if (obj instanceof Class) {
            obj = null;
        }
        setAccessible(field);
        return field.get(obj);
    }

    // ----------------------------------------------------------------------------------------- Scan classes


    public static Class<?>[] getClasses(Object... objects) {
        Class<?>[] classes = new Class<?>[objects.length];
        Object obj;
        for (int i = 0; i < objects.length; i++) {
            obj = objects[i];
            classes[i] = (null == obj) ? Object.class : obj.getClass();
        }
        return classes;
    }

    public static boolean isPublic(Method method) {
        Assert.notNull(method, "Method to provided is null.");
        return Modifier.isPublic(method.getModifiers());
    }

    public static boolean isPublic(Class clazz) {
        Assert.notNull(clazz, "Method to provided is null.");
        return Modifier.isPublic(clazz.getModifiers());
    }

    public static boolean isStatic(Method method) {
        Assert.notNull(method, "Method to provided is null.");
        return Modifier.isStatic(method.getModifiers());
    }

    public static boolean isStatic(Class clazz) {
        Assert.notNull(clazz, "Method to provided is null.");
        return Modifier.isStatic(clazz.getModifiers());
    }

    public static boolean isAbstract(Method method) {
        return Modifier.isAbstract(method.getModifiers());
    }

    public static boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    public static <T extends AccessibleObject> T setAccessible(T accessibleObject) {
        if (null != accessibleObject && false == accessibleObject.isAccessible()) {
            accessibleObject.setAccessible(true);
        }
        return accessibleObject;
    }

    public static boolean isEnum(Class<?> clazz) {
        return null != clazz && clazz.isEnum();
    }
}
