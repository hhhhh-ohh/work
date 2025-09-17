package com.wanmi.sbc.common.plugin.holder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 所有的Service代理缓存持有
 * @author zhengyang
 * @className ServiceProxyCaching
 * @date 2021/6/25 10:06
 **/
public final class ServiceProxyCaching {

    private static Map<String,Object> cachingMap = new ConcurrentHashMap<>();

    public static <T> T getService(String name, Class<T> cls) {
        if(cachingMap.containsKey(name)){
            return (T) cachingMap.get(name);
        }
        return null;
    }

    public static void setService(String name, Object install) {
        cachingMap.put(name, install);
    }

    public static void loopMap(Consumer<Map.Entry<String, Object>> consumer){
        for (Map.Entry<String, Object> service : cachingMap.entrySet()) {
            consumer.accept(service);
        }
    }
}
