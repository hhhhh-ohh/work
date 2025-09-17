package com.wanmi.sbc.common.util;

import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author zhengyang
 * @className NamedThreadFactory
 * @description 简版支持命名的线程工厂
 * @date 2021/6/2 15:38
 **/

public class NamedThreadFactory implements ThreadFactory {
    private static final Map<String, AtomicInteger> PREFIX_COUNTER = new ConcurrentHashMap<>();
    private final ThreadGroup group;
    private final AtomicInteger counter;
    private final String prefix;

    public NamedThreadFactory(String prefix) {
        this.counter = new AtomicInteger(0);
        int prefixCounter = ((AtomicInteger) computeIfAbsent(PREFIX_COUNTER, prefix, (key) -> {
            return new AtomicInteger(0);
        })).incrementAndGet();
        SecurityManager securityManager = System.getSecurityManager();
        this.group = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.prefix = prefix + "_" + prefixCounter;
    }

    /****
     * 创建一个带名称的线程
     * @param r
     * @return
     */
    @Override
    public Thread newThread(Runnable r) {
        String name = this.prefix + "_" + this.counter.incrementAndGet();
        Thread thread = new FastThreadLocalThread(this.group, r, name);
        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }

        return thread;
    }

    public static <K, V> V computeIfAbsent(
            Map<K, V> map, K key, Function<? super K, ? extends V> mappingFunction) {
        V value = map.get(key);
        return value != null ? value : map.computeIfAbsent(key, mappingFunction);
    }
}