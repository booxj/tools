package com.booxj.tools.core.utils;

import com.booxj.tools.core.annotation.Alias;
import com.booxj.tools.core.exceptions.UtilException;
import com.booxj.tools.core.lang.Assert;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

public class ReflectUtil {

    /**
     * 构造对象缓存
     */
    private static final WeakHashMap<Class<?>, Constructor<?>[]> CONSTRUCTORS_CACHE = new WeakHashMap<>();
    /**
     * 字段缓存
     */
    private static final WeakHashMap<Class<?>, Field[]> FIELDS_CACHE = new WeakHashMap<>();
    /**
     * 方法缓存
     */
    private static final WeakHashMap<Class<?>, Method[]> METHODS_CACHE = new WeakHashMap<>();

    // --------------------------------------------------------------------------------------------------------- Constructor

    /**
     * 查找类中的指定参数的构造方法，如果找到构造方法，会自动设置可访问为true
     *
     * @param <T>            对象类型
     * @param clazz          类
     * @param parameterTypes 参数类型，只要任何一个参数是指定参数的父类或接口或相等即可，此参数可以不传
     * @return 构造方法，如果未找到返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * 获得一个类中所有构造列表
     *
     * @param <T>       构造的对象类型
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T>[] getConstructors(Class<T> beanClass) throws SecurityException {
        Assert.notNull(beanClass);
        Constructor<?>[] constructors = CONSTRUCTORS_CACHE.get(beanClass);
        if (null != constructors) {
            return (Constructor<T>[]) constructors;
        }

        constructors = getConstructorsDirectly(beanClass);
        return (Constructor<T>[]) CONSTRUCTORS_CACHE.put(beanClass, constructors);
    }

    /**
     * 获得一个类中所有字段列表，直接反射获取，无缓存
     *
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Constructor<?>[] getConstructorsDirectly(Class<?> beanClass) throws SecurityException {
        Assert.notNull(beanClass);
        return beanClass.getDeclaredConstructors();
    }

    // --------------------------------------------------------------------------------------------------------- Field

    /**
     * 获取字段名，如果存在{@link Alias}注解，读取注解的值作为名称
     *
     * @param field 字段
     * @return 字段名
     */
    public static String getFieldName(Field field) {
        if (null == field) {
            return null;
        }

        final Alias alias = field.getAnnotation(Alias.class);
        if (null != alias) {
            return alias.value();
        }

        return field.getName();
    }

    /**
     * 查找指定类中的所有字段（包括非public字段），也包括父类和Object类的字段， 字段不存在则返回<code>null</code>
     *
     * @param beanClass 被查找字段的类,不能为null
     * @param name      字段名
     * @return 字段
     * @throws SecurityException 安全异常
     */
    public static Field getField(Class<?> beanClass, String name) throws SecurityException {
        final Field[] fields = getFields(beanClass);
        if (ArrayUtil.isNotEmpty(fields)) {
            for (Field field : fields) {
                if ((name.equals(getFieldName(field)))) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * 获得一个类中所有字段列表，包括其父类中的字段
     *
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field[] getFields(Class<?> beanClass) throws SecurityException {
        Field[] allFields = FIELDS_CACHE.get(beanClass);
        if (null != allFields) {
            return allFields;
        }

        allFields = getFieldsDirectly(beanClass, true);
        return FIELDS_CACHE.put(beanClass, allFields);
    }

    /**
     * 获得一个类中所有字段列表，直接反射获取，无缓存
     *
     * @param beanClass            类
     * @param withSuperClassFields 是否包括父类的字段列表
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field[] getFieldsDirectly(Class<?> beanClass, boolean withSuperClassFields) throws SecurityException {
        Assert.notNull(beanClass);

        Field[] allFields = null;
        Class<?> searchType = beanClass;
        Field[] declaredFields;
        while (searchType != null) {
            declaredFields = searchType.getDeclaredFields();
            if (null == allFields) {
                allFields = declaredFields;
            } else {
                allFields = ArrayUtil.append(allFields, declaredFields);
            }
            searchType = withSuperClassFields ? searchType.getSuperclass() : null;
        }

        return allFields;
    }

    /**
     * 获取字段值
     *
     * @param obj       对象，如果static字段，此处为类
     * @param fieldName 字段名
     * @return 字段值
     * @throws UtilException 包装IllegalAccessException异常
     */
    public static Object getFieldValue(Object obj, String fieldName) throws UtilException {
        if (null == obj || StringUtil.isBlank(fieldName)) {
            return null;
        }
        return getFieldValue(obj, getField(obj instanceof Class ? (Class<?>) obj : obj.getClass(), fieldName));
    }

    /**
     * 获取字段值
     *
     * @param obj   对象，static字段则此字段为null
     * @param field 字段
     * @return 字段值
     * @throws UtilException 包装IllegalAccessException异常
     */
    public static Object getFieldValue(Object obj, Field field) throws UtilException {
        if (null == field) {
            return null;
        }
        if (obj instanceof Class) {
            // 静态字段获取时对象为null
            obj = null;
        }

        setAccessible(field);
        Object result;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            throw new UtilException(e, "IllegalAccess for {}.{}", field.getDeclaringClass(), field.getName());
        }
        return result;
    }

    /**
     * 设置字段值
     *
     * @param obj       对象,static字段则此处传Class
     * @param fieldName 字段名
     * @param value     值，值类型必须与字段类型匹配，不会自动转换对象类型
     * @throws UtilException 包装IllegalAccessException异常
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) throws UtilException {
        Assert.notNull(obj);
        Assert.notBlank(fieldName);

        final Field field = getField((obj instanceof Class) ? (Class<?>) obj : obj.getClass(), fieldName);
        Assert.notNull(field, "Field [{}] is not exist in [{}]", fieldName, obj.getClass().getName());
        setFieldValue(obj, field, value);
    }

    /**
     * 设置字段值
     *
     * @param obj   对象，如果是static字段，此参数为null
     * @param field 字段
     * @param value 值，值类型必须与字段类型匹配，不会自动转换对象类型
     * @throws UtilException UtilException 包装IllegalAccessException异常
     */
    public static void setFieldValue(Object obj, Field field, Object value) throws UtilException {
        Assert.notNull(field, "Field in [{}] not exist !", obj);
        try {
            setAccessible(field);
            field.set(obj instanceof Class ? null : obj, value);
        } catch (IllegalAccessException e) {
            throw new UtilException(e, "IllegalAccess for {}.{}", obj, field.getName());
        }
    }

    // --------------------------------------------------------------------------------------------------------- method

    /**
     * 获得指定类本类及其父类中的Public方法名<br>
     * 去重重载的方法
     *
     * @param clazz 类
     * @return 方法名Set
     */
    public static Set<String> getPublicMethodNames(Class<?> clazz) {
        final HashSet<String> methodSet = new HashSet<>();
        final Method[] methodArray = getPublicMethods(clazz);
        if (ArrayUtil.isNotEmpty(methodArray)) {
            for (Method method : methodArray) {
                methodSet.add(method.getName());
            }
        }
        return methodSet;
    }

    /**
     * 获得本类及其父类所有Public方法
     *
     * @param clazz 查找方法的类
     * @return 过滤后的方法列表
     */
    public static Method[] getPublicMethods(Class<?> clazz) {
        return null == clazz ? null : clazz.getMethods();
    }

    /**
     * 查找指定Public方法 如果找不到对应的方法或方法不为public的则返回<code>null</code>
     *
     * @param clazz      类
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getPublicMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) throws SecurityException {
        try {
            return clazz.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    public static Method getPublicMethod(Object obj, String methodName, Object... args) throws SecurityException {
        if (null == obj || StringUtil.isBlank(methodName)) {
            return null;
        }
        return getPublicMethod(obj.getClass(), methodName, ClassUtil.getClasses(args));
    }

    /**
     * 获得指定类中的Public方法名<br>
     * 去重重载的方法
     *
     * @param clazz 类
     * @return 方法名Set
     * @throws SecurityException 安全异常
     */
    public static Set<String> getMethodNames(Class<?> clazz) throws SecurityException {
        final HashSet<String> methodSet = new HashSet<>();
        final Method[] methods = getMethods(clazz);
        for (Method method : methods) {
            methodSet.add(method.getName());
        }
        return methodSet;
    }

    /**
     * 获得一个类中所有方法列表，包括其父类中的方法
     *
     * @param beanClass 类
     * @return 方法列表
     * @throws SecurityException 安全检查异常
     */
    public static Method[] getMethods(Class<?> beanClass) throws SecurityException {
        Method[] allMethods = METHODS_CACHE.get(beanClass);
        if (null != allMethods) {
            return allMethods;
        }

        allMethods = getMethodsDirectly(beanClass, true);
        return METHODS_CACHE.put(beanClass, allMethods);
    }

    /**
     * 获得一个类中所有方法列表，直接反射获取，无缓存
     *
     * @param beanClass             类
     * @param withSuperClassMethods 是否包括父类的方法列表
     * @return 方法列表
     * @throws SecurityException 安全检查异常
     */
    public static Method[] getMethodsDirectly(Class<?> beanClass, boolean withSuperClassMethods) throws SecurityException {
        Assert.notNull(beanClass);

        Method[] allMethods = null;
        Class<?> searchType = beanClass;
        Method[] declaredMethods;
        while (searchType != null) {
            declaredMethods = searchType.getDeclaredMethods();
            if (null == allMethods) {
                allMethods = declaredMethods;
            } else {
                allMethods = ArrayUtil.append(allMethods, declaredMethods);
            }
            searchType = withSuperClassMethods ? searchType.getSuperclass() : null;
        }

        return allMethods;
    }

    /**
     * 是否为equals方法
     *
     * @param method 方法
     * @return 是否为equals方法
     */
    public static boolean isEqualsMethod(Method method) {
        if (method == null || false == "equals".equals(method.getName())) {
            return false;
        }
        final Class<?>[] paramTypes = method.getParameterTypes();
        return (1 == paramTypes.length && paramTypes[0] == Object.class);
    }

    /**
     * 是否为hashCode方法
     *
     * @param method 方法
     * @return 是否为hashCode方法
     */
    public static boolean isHashCodeMethod(Method method) {
        return method != null//
                && "hashCode".equals(method.getName())//
                && isEmptyParam(method);
    }

    /**
     * 是否为toString方法
     *
     * @param method 方法
     * @return 是否为toString方法
     */
    public static boolean isToStringMethod(Method method) {
        return method != null//
                && "toString".equals(method.getName())//
                && isEmptyParam(method);
    }

    /**
     * 是否为无参数方法
     *
     * @param method 方法
     * @return 是否为无参数方法
     */
    public static boolean isEmptyParam(Method method) {
        return method.getParameterTypes().length == 0;
    }

    // --------------------------------------------------------------------------------------------------------- newInstance

    /**
     * 实例化对象
     *
     * @param <T>   对象类型
     * @param clazz 类名
     * @return 对象
     * @throws UtilException 包装各类异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String clazz) throws UtilException {
        try {
            return (T) Class.forName(clazz).newInstance();
        } catch (Exception e) {
            throw new UtilException(e, "Instance class [{}] error!", clazz);
        }
    }

    /**
     * 实例化对象
     *
     * @param <T>    对象类型
     * @param clazz  类
     * @param params 构造函数参数
     * @return 对象
     * @throws UtilException 包装各类异常
     */
    public static <T> T newInstance(Class<T> clazz, Object... params) throws UtilException {
        if (ArrayUtil.isEmpty(params)) {
            final Constructor<T> constructor = getConstructor(clazz);
            try {
                return constructor.newInstance();
            } catch (Exception e) {
                throw new UtilException(e, "Instance class [{}] error!", clazz);
            }
        }

        final Class<?>[] paramTypes = ClassUtil.getClasses(params);
        final Constructor<T> constructor = getConstructor(clazz, paramTypes);
        if (null == constructor) {
            throw new UtilException("No Constructor matched for parameter types: [{}]", new Object[]{paramTypes});
        }
        try {
            return constructor.newInstance(params);
        } catch (Exception e) {
            throw new UtilException(e, "Instance class [{}] error!", clazz);
        }
    }

    // --------------------------------------------------------------------------------------------------------- invoke

    /**
     * 执行静态方法
     *
     * @param <T>    对象类型
     * @param method 方法（对象方法或static方法都可）
     * @param args   参数对象
     * @return 结果
     * @throws UtilException 多种异常包装
     */
    public static <T> T invokeStatic(Method method, Object... args) throws UtilException {
        return invoke(null, method, args);
    }

    /**
     * 执行方法
     *
     * <p>
     * 对于用户传入参数会做必要检查，包括：
     *
     * <pre>
     *     1、忽略多余的参数
     *     2、参数不够补齐默认值
     *     3、传入参数为null，但是目标参数类型为原始类型，做转换
     * </pre>
     *
     * @param <T>    返回对象类型
     * @param obj    对象，如果执行静态方法，此值为<code>null</code>
     * @param method 方法（对象方法或static方法都可）
     * @param args   参数对象
     * @return 结果
     * @throws UtilException 一些列异常的包装
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object obj, Method method, Object... args) throws UtilException {
        setAccessible(method);

        // 检查用户传入参数：
        // 1、忽略多余的参数
        // 2、参数不够补齐默认值
        // 3、传入参数为null，但是目标参数类型为原始类型，做转换
        // 4、传入参数类型不对应，尝试转换类型
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Object[] actualArgs = new Object[parameterTypes.length];
        if (null != args) {
            for (int i = 0; i < actualArgs.length; i++) {
                if (i >= args.length || null == args[i]) {
                    // 越界或者空值
                    actualArgs[i] = null;
                } else {
                    actualArgs[i] = args[i];
                }
            }
        }

        try {
            return (T) method.invoke(ClassUtil.isStatic(method) ? null : obj, actualArgs);
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * 执行对象中指定方法
     *
     * @param <T>        返回对象类型
     * @param obj        方法所在对象
     * @param methodName 方法名
     * @param args       参数列表
     * @return 执行结果
     * @throws UtilException IllegalAccessException包装
     */
    public static <T> T invoke(Object obj, String methodName, Object... args) throws UtilException {
        final Method method = getPublicMethod(obj, methodName, args);
        if (null == method) {
            throw new UtilException(StringUtil.format("No such method: [{}]", methodName));
        }
        return invoke(obj, method, args);
    }

    /**
     * 设置方法为可访问（私有方法可以被外部调用）
     *
     * @param <T>              AccessibleObject的子类，比如Class、Method、Field等
     * @param accessibleObject 可设置访问权限的对象，比如Class、Method、Field等
     * @return 被设置可访问的对象
     */
    public static <T extends AccessibleObject> T setAccessible(T accessibleObject) {
        if (null != accessibleObject && false == accessibleObject.isAccessible()) {
            accessibleObject.setAccessible(true);
        }
        return accessibleObject;
    }
}
