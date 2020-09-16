package com.booxj.tools.core.thread;

import com.booxj.tools.core.builder.Builder;
import com.booxj.tools.core.utils.StringUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactoryBuilder implements Builder<ThreadFactory> {

    private static final long serialVersionUID = 1L;

    private static final ThreadFactory DEFAULT_THREAD_FACTORY = Executors.defaultThreadFactory();

    private static final AtomicInteger poolNumber = new AtomicInteger(1);

    /**
     * 用于线程创建的线程工厂类
     */
    private ThreadFactory backingThreadFactory;
    /**
     * 线程名的前缀
     */
    private String namePrefix = "";
    /**
     * 是否守护线程，默认false
     */
    private Boolean daemon;
    /**
     * 线程优先级
     */
    private Integer priority;
    /**
     * 未捕获异常处理器
     */
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public static ThreadFactoryBuilder create() {
        return new ThreadFactoryBuilder();
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = backingThreadFactory;
        return this;
    }

    public ThreadFactoryBuilder setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
        return this;
    }

    public ThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public ThreadFactoryBuilder setPriority(int priority) {
        if (priority < Thread.MIN_PRIORITY) {
            throw new IllegalArgumentException(StringUtil.format("Thread priority ({}) must be >= {}", priority, Thread.MIN_PRIORITY));
        }
        if (priority > Thread.MAX_PRIORITY) {
            throw new IllegalArgumentException(StringUtil.format("Thread priority ({}) must be <= {}", priority, Thread.MAX_PRIORITY));
        }
        this.priority = priority;
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }

    @Override
    public ThreadFactory build() {
        return build(this);
    }

    private static ThreadFactory build(ThreadFactoryBuilder builder) {
        final ThreadFactory backingThreadFactory = (null != builder.backingThreadFactory)
                ? builder.backingThreadFactory
                : DEFAULT_THREAD_FACTORY;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix = builder.namePrefix = builder.namePrefix + "-" + poolNumber.getAndIncrement() + "-thread-";
        final Boolean daemon = builder.daemon = (null != builder.daemon) ? false : builder.daemon;
        final Integer priority = builder.priority = (null != builder.priority) ? builder.priority : Thread.NORM_PRIORITY;
        final Thread.UncaughtExceptionHandler handler = builder.uncaughtExceptionHandler;

        return r -> {
            final Thread thread = backingThreadFactory.newThread(r);

            thread.setName(namePrefix + threadNumber.getAndIncrement());
            thread.setDaemon(daemon);
            thread.setPriority(priority);

            if (null != handler) {
                thread.setUncaughtExceptionHandler(handler);
            }
            return thread;
        };
    }
}
