package com.wanmi.sbc.common.cache;

import com.alibaba.fastjson2.support.spring6.data.redis.GenericFastJsonRedisSerializer;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import com.wanmi.sbc.common.config.redis.RedisCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.*;

import static com.wanmi.sbc.common.cache.CacheConstants.GLOBAL_CACHE_NAME;

/**
 * @author zhanggaolei
 * @className CacheConfigManager
 * @description
 * @date 2022/5/6 15:11
 */
@Slf4j
@EnableCaching
@Configuration
@ConditionalOnProperty(prefix = "wm.cache", value = "enabled")
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class CacheConfigManager {

    @Autowired private CacheProperties cacheProperties;

    /**
     * create redisTemplate
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     */
    @Bean
    @Conditional(RedisCondition.class)
    @ConditionalOnMissingBean(name = "stringKeyRedisTemplate")
    public RedisTemplate<Object, Object> stringKeyRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        RedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setValueSerializer(new GenericFastJsonRedisSerializer());
        template.setHashValueSerializer(new GenericFastJsonRedisSerializer());
        return template;
    }

    //    /**
    //     * 创建基于Caffeine的Cache Manager
    //     *
    //     * @return
    //     */
    //    public CacheManager caffeineCacheManager() {
    //
    //        log.debug("caffeine-plus create cacheManager");
    //
    //        SimpleCacheManager cacheManager = new SimpleCacheManager();
    //        Map<String, CaffeineCache> cacheMap = new HashMap();
    //
    //        // 设置全局配置的本地缓存
    //
    //        cacheMap.put(
    //                GLOBAL_CACHE_NAME,
    //                getCaffeineCache(GLOBAL_CACHE_NAME, cacheProperties.getDefaultSpec()));
    //
    //        // 设置自定义属性缓存, 可以覆盖全局缓存
    //        List<CacheProperties.Config> configs = cacheProperties.getConfigs();
    //        if (null != configs && !configs.isEmpty()) {
    //            for (CacheProperties.Config config : configs) {
    //                List<String> cacheNames = config.getCacheName();
    //                if (null == cacheNames || cacheNames.isEmpty()) {
    //                    continue;
    //                }
    //                for (String cacheName : cacheNames) {
    //                    cacheMap.put(cacheName, getCaffeineCache(cacheName, config.getSpec()));
    //                }
    //            }
    //        }
    //        // 加入到缓存管理器进行管理
    //        cacheManager.setCaches(cacheMap.values());
    //
    //        return cacheManager;
    //    }

    /**
     * create caffeine cache manager
     * @return CacheManager
     */
    @Bean(value = CacheType.LOCAL)
    @Primary
    public CacheManager caffeineCacheManager() {

        log.debug("caffeine-plus create cacheManager");

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        // set global setting on local cache
        cacheManager.setCaffeineSpec(parseCaffeineSpec(cacheProperties.getDefaultSpec()));
        cacheManager.setAllowNullValues(cacheProperties.isAllowNullValues());
        Map<String, com.github.benmanes.caffeine.cache.Cache> cacheMap = new HashMap();
        cacheMap.put(GLOBAL_CACHE_NAME, getCaffeineCache(cacheProperties.getDefaultSpec()));

        // set custom option
        List<CacheProperties.Config> configs = cacheProperties.getConfigs();
        if (null != configs && !configs.isEmpty()) {
            for (CacheProperties.Config config : configs) {
                List<String> cacheNames = config.getCacheName();
                if (null == cacheNames || cacheNames.isEmpty()) {
                    continue;
                }
                for (String cacheName : cacheNames) {
                    if (StringUtils.isNotBlank(config.getSpec())) {
                        cacheMap.put(cacheName, getCaffeineCache(config.getSpec()));
                    } else {
                        cacheMap.put(cacheName, getCaffeineCache(cacheProperties.getDefaultSpec()));
                    }
                }
            }
        }
        // register cache manager
        cacheMap.entrySet()
                .forEach(
                        entry -> {
                            cacheManager.registerCustomCache(entry.getKey(), entry.getValue());
                        });

        return cacheManager;
    }

    //    @Bean(value = CacheConstants.REDIS)
    //    @Conditional(RedisCondition.class)
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration defaultRedisCacheConfiguration =
                getRedisCache(cacheProperties.getDefaultSpec());
        //                defaultRedisCacheConfiguration =
        //
        // defaultRedisCacheConfiguration.prefixCacheNameWith(GLOBAL_CACHE_NAME);

        Set<String> cacheNameSet = new HashSet<>();

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> cacheMap = new HashMap<>();
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
                    cacheNameSet.add(cacheName);
                    cacheMap.put(cacheName, getRedisCache(spec));
                }
            }
        }

        RedisCacheManager cacheManager =
                RedisCacheManager.builder(redisConnectionFactory)
                        .cacheDefaults(defaultRedisCacheConfiguration)
                        .initialCacheNames(cacheNameSet)
                        .withInitialCacheConfigurations(cacheMap)
                        .build();
        return cacheManager;
    }

    @Bean(value = CacheType.REDIS)
    @Conditional(RedisCondition.class)
    public CacheManager redisCacheManager(
            @Qualifier("stringKeyRedisTemplate") RedisTemplate<Object, Object> redisTemplate) {
        RedisCacheConfiguration defaultRedisCacheConfiguration =
                getRedisCache(cacheProperties.getDefaultSpec());
        //此处将双冒号改为单冒号
        defaultRedisCacheConfiguration =
                defaultRedisCacheConfiguration.computePrefixWith(cacheName -> cacheName + ":");
        Set<String> cacheNameSet = new HashSet<>();

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> cacheMap = new HashMap<>();
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
                    cacheNameSet.add(cacheName);
                    cacheMap.put(cacheName, getRedisCache(spec));
                }
            }
        }

        com.wanmi.sbc.common.cache.RedisCacheManager cacheManager =
                com.wanmi.sbc.common.cache.RedisCacheManager.builder(redisTemplate)
                        .cacheDefaults(defaultRedisCacheConfiguration)
                        .initialCacheNames(cacheNameSet)
                        .withInitialCacheConfigurations(cacheMap)
                        .build();
        return cacheManager;
    }

    @Bean(value = CacheType.MULTI)
    @Conditional(RedisCondition.class)
    @ConditionalOnBean(RedisTemplate.class)
    public CacheManager multiCacheManager(
            @Qualifier("stringKeyRedisTemplate") RedisTemplate<Object, Object> redisTemplate) {
        return new MultiCacheManager(cacheProperties, redisTemplate);
    }

    @Bean
    @Conditional(RedisCondition.class)
    @ConditionalOnClass(RedisTemplate.class)
    public RedisMessageListenerContainer redisMessageListenerContainer(
            @Qualifier("stringKeyRedisTemplate") RedisTemplate<Object, Object> redisTemplate) {
        if (StringUtils.isNotBlank(cacheProperties.getTopic())) {
            RedisMessageListenerContainer redisMessageListenerContainer =
                    new RedisMessageListenerContainer();
            redisMessageListenerContainer.setConnectionFactory(
                    redisTemplate.getConnectionFactory());
            CacheMessageListener cacheMessageListener =
                    new CacheMessageListener(redisTemplate);
            redisMessageListenerContainer.addMessageListener(
                    cacheMessageListener, new ChannelTopic(cacheProperties.getTopic()));
            return redisMessageListenerContainer;
        }
        return null;
    }

    /**
     * 添加缓存对象
     *
     * <p>不支持refreshAfterWrite参数 refreshAfterWrite参数需要配合cacheLoader使用，需要自定义cacheManager
     *
     * @param spec
     */
    private com.github.benmanes.caffeine.cache.Cache getCaffeineCache(String spec) {

        CacheSpec cacheSpec = CacheSpec.parse(spec);
        /** 初始化caffeine对象 */
        Caffeine<Object, Object> caffeine = Caffeine.from(cacheSpec.getCaffeineSpec());
        //        caffeine.removalListener((key, value, cause) ->
        //                log.debug("缓存键 [{}], 缓存值 [{}] 被淘汰的原因为: [{}]", key, value, cause));
        Cache<Object, Object> cache = caffeine.build();

        return cache;
    }

    private CaffeineSpec parseCaffeineSpec(String spec) {
        CacheSpec cacheSpec = CacheSpec.parse(spec);
        return CaffeineSpec.parse(cacheSpec.getCaffeineSpec());
    }

    private RedisCacheConfiguration getRedisCache(String spec) {
        CacheSpec cacheSpec = CacheSpec.parse(spec);
        RedisCacheConfiguration redisCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存管理器管理的缓存的默认过期时间
        redisCacheConfig =
                redisCacheConfig
                        .entryTtl(Duration.ofSeconds(cacheSpec.getExpireTime()))
                        // 设置 key为string序列化
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new StringRedisSerializer()))
                        // 设置value为json序列化
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new GenericFastJsonRedisSerializer()));
        if (!cacheProperties.isAllowNullValues()) {
            // 不缓存空值
            redisCacheConfig = redisCacheConfig.disableCachingNullValues();
        }
        return redisCacheConfig;
    }
}
