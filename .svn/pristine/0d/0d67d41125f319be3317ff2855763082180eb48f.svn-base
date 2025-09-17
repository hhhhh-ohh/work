package com.wanmi.sbc.common.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.wanmi.sbc.common.cache.CacheConstants.GLOBAL_CACHE_NAME;

/**
 * @author zhanggaolei
 * @className MultiCacheManager
 * @description
 * @date 2022/5/9 14:08
 */
@Slf4j
public class MultiCacheManager implements CacheManager {

    private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    private CacheProperties cacheProperties;

    private boolean dynamic;

    private Set<String> cacheNames;

    private long expire = -1;

    private RedisTemplate<Object, Object> redisTemplate;

    public MultiCacheManager(CacheProperties cacheProperties, RedisTemplate redisTemplate) {
        super();
        this.cacheProperties = cacheProperties;
        this.dynamic = cacheProperties.isDynamic();
        this.redisTemplate = redisTemplate;
        build();
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache != null) {
            return cache;
        }
        if (!dynamic && !cacheNames.contains(name)) {
            return cache;
        }

        cache =
                new MultiCache(
                        name,
                        redisTemplate,
                        getCaffeineCache(cacheProperties.getDefaultSpec()),
                        cacheProperties,
                        expire);
        Cache oldCache = cacheMap.putIfAbsent(name, cache);
        log.debug("create cache instance, the cache name is : {}", name);
        return oldCache == null ? cache : oldCache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return this.cacheNames;
    }

    public void clearLocal(String cacheName, Object key) {
        Cache cache = cacheMap.get(cacheName);
        if (cache == null) {
            return;
        }

        MultiCache multiCache = (MultiCache) cache;
        multiCache.clearLocal(key);
    }

    private void build() {
        this.expire = CacheSpec.parse(cacheProperties.getDefaultSpec()).getExpireTime();
        // 设置全局配置的本地缓存
        cacheMap.put(
                GLOBAL_CACHE_NAME,
                new MultiCache(
                        GLOBAL_CACHE_NAME,
                        redisTemplate,
                        getCaffeineCache(cacheProperties.getDefaultSpec()),
                        cacheProperties,
                        expire));

        // 设置自定义属性缓存, 可以覆盖全局缓存
        List<CacheProperties.Config> configs = cacheProperties.getConfigs();
        if (null != configs && !configs.isEmpty()) {
            for (CacheProperties.Config config : configs) {
                List<String> cacheNames = config.getCacheName();
                if (null == cacheNames || cacheNames.isEmpty()) {
                    continue;
                }
                for (String cacheName : cacheNames) {
                    String spec = config.getSpec();
                    if (StringUtils.isBlank(spec)) {
                        spec = cacheProperties.getDefaultSpec();
                    }
                    CacheSpec cacheSpec = CacheSpec.parse(spec);
                    Long tempExpire = cacheSpec.getExpireTime();
                    if (tempExpire < 0) {
                        tempExpire = expire;
                    }
                    cacheMap.put(
                            cacheName,
                            new MultiCache(
                                    cacheName,
                                    redisTemplate,
                                    getCaffeineCache(cacheSpec),
                                    cacheProperties,
                                    tempExpire));
                }
            }
        }
        // 加入到缓存管理器进行管理
        cacheNames = cacheMap.keySet();
    }

    /**
     * 添加缓存对象
     *
     * <p>不支持refreshAfterWrite参数 refreshAfterWrite参数需要配合cacheLoader使用，需要自定义cacheManager
     *
     * @param spec
     */
    private com.github.benmanes.caffeine.cache.Cache<Object, Object> getCaffeineCache(String spec) {

        CacheSpec cacheSpec = CacheSpec.parse(spec);
        /** 初始化caffeine对象 */
        Caffeine<Object, Object> caffeine = Caffeine.from(cacheSpec.getCaffeineSpec());
        //        caffeine.removalListener((key, value, cause) ->
        //                log.debug("缓存键 [{}], 缓存值 [{}] 被淘汰的原因为: [{}]", key, value, cause));
        com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = caffeine.build();

        return cache;
    }

    /**
     * 添加缓存对象
     *
     * <p>不支持refreshAfterWrite参数 refreshAfterWrite参数需要配合cacheLoader使用，需要自定义cacheManager
     *
     * @param cacheSpec
     */
    private com.github.benmanes.caffeine.cache.Cache<Object, Object> getCaffeineCache(
            CacheSpec cacheSpec) {

        /** 初始化caffeine对象 */
        Caffeine<Object, Object> caffeine = Caffeine.from(cacheSpec.getCaffeineSpec());
        //        caffeine.removalListener((key, value, cause) ->
        //                log.debug("缓存键 [{}], 缓存值 [{}] 被淘汰的原因为: [{}]", key, value, cause));
        com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = caffeine.build();

        return cache;
    }
}
