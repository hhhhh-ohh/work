package com.wanmi.sbc.common.cache;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className CaffeineCacheUtils
 * @description
 * @date 2022/5/6 13:41
 */
@Component
@ConditionalOnBean(CacheConfigManager.class)
public class CaffeineCacheUtils {
    @Autowired private CacheManager caffeineCacheManager;

    /**
     * 提取缓存的值
     *
     * @param cacheName
     * @param key
     * @return
     */
    public Object getCacheValue(String cacheName, Object key) {

        if (null == caffeineCacheManager.getCache(cacheName)) {
            return null;
        }

        if (null == caffeineCacheManager.getCache(cacheName).get(key)) {
            return null;
        }

        return caffeineCacheManager.getCache(cacheName).get(key).get();
    }

    /**
     * if cacheName not exists, throw {@link SbcRuntimeException}
     *
     * <p>if key not exists, create new CaffeineCache. key exists, update it.
     *
     * @param cacheName
     * @param key
     * @param value
     */
    public void putCache(String cacheName, Object key, Object value) {

        Cache cache = caffeineCacheManager.getCache(cacheName);

        if (null == cache) {
            throw new SbcRuntimeException(
                    CommonErrorCodeEnum.K999999, "cache [" + cacheName + "] not exists.");
        }

        CaffeineCache caffeineCache = (CaffeineCache) cache;
        caffeineCache.put(key, value);
    }

    /**
     * 查询缓存清单
     *
     * @return
     */
    public Collection<String> listCacheNames() {
        return caffeineCacheManager.getCacheNames();
    }

    /**
     * 查询缓存性能参数
     *
     * @return
     */
    public Map<String, CacheStats> listCachesStats() {

        Map<String, CacheStats> cacheStatses = new HashMap<>();

        Collection<String> cacheNames = listCacheNames();

        cacheNames.forEach(
                cacheName -> {
                    CaffeineCache caffeineCache =
                            (CaffeineCache) caffeineCacheManager.getCache(cacheName);
                    com.github.benmanes.caffeine.cache.Cache<Object, Object> cache =
                            caffeineCache.getNativeCache();
                    CacheStats cacheStats = cache.stats();
                    cacheStatses.put(cacheName, cacheStats);
                });

        return cacheStatses;
    }

    /**
     * 移除某个缓存
     *
     * <p>if cacheName not exists, throw {@link SbcRuntimeException}
     *
     * @param cacheName
     * @param key
     */
    public void evictCache(String cacheName, Object key) {

        Cache cache = caffeineCacheManager.getCache(cacheName);

        if (null == cache) {
            throw new SbcRuntimeException(
                    CommonErrorCodeEnum.K999999, "cache [" + cacheName + "] not exists.");
        }

        cache.evict(key);
    }

    /**
     * 清空缓存
     *
     * <p>if cacheName not exists, throw {@link SbcRuntimeException}
     *
     * @param cacheName
     */
    public void clearCaches(String cacheName) {

        Cache cache = caffeineCacheManager.getCache(cacheName);

        if (null == cache) {
            throw new SbcRuntimeException(
                    CommonErrorCodeEnum.K999999, "cache [" + cacheName + "] not exists.");
        }

        cache.clear();
    }
}
