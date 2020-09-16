//package com.booxj.tools.system;
//
//import com.booxj.tools.core.utils.StringUtil;
//
//import java.lang.management.*;
//import java.util.List;
//
//import static java.lang.System.err;
//
///**
// * Java的System类封装工具类。<br>
// */
//public class SystemUtil {
//
//    public static String get(String name, boolean quiet) {
//        String value = null;
//        try {
//            value = System.getProperty(name);
//        } catch (SecurityException e) {
//            if (false == quiet) {
//                err.println("Caught a SecurityException reading the system property '" + name + "'; the SystemUtil property value will default to null.");
//            }
//        }
//
//        if (null == value) {
//            try {
//                value = System.getenv(name);
//            } catch (SecurityException e) {
//                if (false == quiet) {
//                    err.println("Caught a SecurityException reading the system env '" + name + "'; the SystemUtil env value will default to null.");
//                }
//            }
//        }
//
//        return value;
//    }
//
//    /**
//     * 获取当前进程 PID
//     *
//     * @return 当前进程 ID
//     */
//    public static long getCurrentPID() {
//        return Long.parseLong(getRuntimeMXBean().getName().split("@")[0]);
//    }
//
//    /**
//     * 返回Java虚拟机类加载系统相关属性
//     *
//     * @return {@link ClassLoadingMXBean}
//     */
//    public static ClassLoadingMXBean getClassLoadingMXBean() {
//        return ManagementFactory.getClassLoadingMXBean();
//    }
//
//    /**
//     * 返回Java虚拟机内存系统相关属性
//     *
//     * @return {@link MemoryMXBean}
//     */
//    public static MemoryMXBean getMemoryMXBean() {
//        return ManagementFactory.getMemoryMXBean();
//    }
//
//    /**
//     * 返回Java虚拟机线程系统相关属性
//     *
//     * @return {@link ThreadMXBean}
//     */
//    public static ThreadMXBean getThreadMXBean() {
//        return ManagementFactory.getThreadMXBean();
//    }
//
//    /**
//     * 返回Java虚拟机运行时系统相关属性
//     *
//     * @return {@link RuntimeMXBean}
//     */
//    public static RuntimeMXBean getRuntimeMXBean() {
//        return ManagementFactory.getRuntimeMXBean();
//    }
//
//    /**
//     * 返回Java虚拟机编译系统相关属性<br>
//     * 如果没有编译系统，则返回<code>null</code>
//     *
//     * @return a {@link CompilationMXBean} ，如果没有编译系统，则返回<code>null</code>
//     */
//    public static CompilationMXBean getCompilationMXBean() {
//        return ManagementFactory.getCompilationMXBean();
//    }
//
//    /**
//     * 返回Java虚拟机运行下的操作系统相关信息属性
//     *
//     * @return {@link OperatingSystemMXBean}
//     */
//    public static OperatingSystemMXBean getOperatingSystemMXBean() {
//        return ManagementFactory.getOperatingSystemMXBean();
//    }
//
//    /**
//     * 获取Java虚拟机中的{@link MemoryPoolMXBean}列表<br>
//     * The Java virtual machine can have one or more memory pools. It may add or remove memory pools during execution.
//     *
//     * @return a list of <tt>MemoryPoolMXBean</tt> objects.
//     */
//    public static List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
//        return ManagementFactory.getMemoryPoolMXBeans();
//    }
//
//    /**
//     * 获取Java虚拟机中的{@link GarbageCollectorMXBean}列表
//     *
//     * @return {@link GarbageCollectorMXBean}列表
//     */
//    public static List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
//        return ManagementFactory.getGarbageCollectorMXBeans();
//    }
//
//
//    /**
//     * 取得Java Virtual Machine Specification的信息。
//     *
//     * @return <code>JvmSpecInfo</code>对象
//     */
//    public static JvmSpecInfo getJvmSpecInfo() {
//        return Singleton.get(JvmSpecInfo.class);
//    }
//
//    /**
//     * 取得Java Virtual Machine Implementation的信息。
//     *
//     * @return <code>JvmInfo</code>对象
//     */
//    public static JvmInfo getJvmInfo() {
//        return Singleton.get(JvmInfo.class);
//    }
//
//    /**
//     * 取得Java Specification的信息。
//     *
//     * @return <code>JavaSpecInfo</code>对象
//     */
//    public static JavaSpecInfo getJavaSpecInfo() {
//        return Singleton.get(JavaSpecInfo.class);
//    }
//
//    /**
//     * 取得Java Implementation的信息。
//     *
//     * @return <code>JavaInfo</code>对象
//     */
//    public static JavaInfo getJavaInfo() {
//        return Singleton.get(JavaInfo.class);
//    }
//
//    /**
//     * 取得当前运行的JRE的信息。
//     *
//     * @return <code>JreInfo</code>对象
//     */
//    public static JavaRuntimeInfo getJavaRuntimeInfo() {
//        return Singleton.get(JavaRuntimeInfo.class);
//    }
//
//    /**
//     * 取得OS的信息。
//     *
//     * @return <code>OsInfo</code>对象
//     */
//    public static OsInfo getOsInfo() {
//        return Singleton.get(OsInfo.class);
//    }
//
//    /**
//     * 取得User的信息。
//     *
//     * @return <code>UserInfo</code>对象
//     */
//    public static UserInfo getUserInfo() {
//        return Singleton.get(UserInfo.class);
//    }
//
//    /**
//     * 取得Host的信息。
//     *
//     * @return <code>HostInfo</code>对象
//     */
//    public static HostInfo getHostInfo() {
//        return Singleton.get(HostInfo.class);
//    }
//
//    /**
//     * 获取JVM中内存总大小
//     *
//     * @return 内存总大小
//     */
//    public static long getTotalMemory() {
//        return Runtime.getRuntime().totalMemory();
//    }
//
//    /**
//     * 获取JVM中内存剩余大小
//     *
//     * @return 内存剩余大小
//     */
//    public static long getFreeMemory() {
//        return Runtime.getRuntime().freeMemory();
//    }
//
//    /**
//     * 获取JVM可用的内存总大小
//     *
//     * @return JVM可用的内存总大小
//     */
//    public static long getMaxMemory() {
//        return Runtime.getRuntime().maxMemory();
//    }
//
//    /**
//     * 获取总线程数
//     *
//     * @return 总线程数
//     */
//    public static int getTotalThreadCount() {
//        ThreadGroup parentThread = Thread.currentThread().getThreadGroup();
//        while (null != parentThread.getParent()) {
//            parentThread = parentThread.getParent();
//        }
//        return parentThread.activeCount();
//    }
//
//    /**
//     * 输出到<code>StringBuilder</code>。
//     *
//     * @param builder <code>StringBuilder</code>对象
//     * @param caption 标题
//     * @param value   值
//     */
//    protected static void append(StringBuilder builder, String caption, Object value) {
//        builder.append(caption).append(StringUtil.nullToDefault(value.toString(), "[n/a]")).append("\n");
//    }
//}
