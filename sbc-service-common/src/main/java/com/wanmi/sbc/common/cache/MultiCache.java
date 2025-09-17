package com.wanmi.sbc.common.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.tools.logtrace.core.context.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhanggaolei
 * @className MultiCache
 * @description
 * @date 2022/5/9 14:16
 */
@Slf4j
public class MultiCache extends AbstractValueAdaptingCache {

    private static final byte[] BINARY_NULL_VALUE =
            RedisSerializer.java().serialize(NullValue.INSTANCE);
    /** 缓存名称 */
    private String cacheName;

    /** 一级缓存 */
    private Cache<Object, Object> level1Cache;

    /** 二级缓存实例 */
    private RedisTemplate<Object, Object> level2Cache;

    private long expire = -1;

    private CacheProperties cacheProperties;

    private String topic = "cache:redis:caffeine:topic";

    private Map<String, ReentrantLock> keyLockMap = new ConcurrentHashMap<String, ReentrantLock>();

    protected MultiCache(boolean allowNullValues) {
        super(allowNullValues);
    }

    public MultiCache(
            String cacheName,
            RedisTemplate<Object, Object> level2Cache,
            Cache<Object, Object> level1Cache,
            CacheProperties cacheProperties,
            long expire) {
        super(cacheProperties.isAllowNullValues());
        this.cacheName = cacheName;
        this.level2Cache = level2Cache;
        this.level1Cache = level1Cache;
        this.expire = expire;
        this.topic = cacheProperties.getTopic();
        this.cacheProperties = cacheProperties;
    }

    @Override
    public String getName() {
        return this.cacheName;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object value = lookup(key);
        if (value != null) {
            return (T) value;
        }

        ReentrantLock lock = keyLockMap.get(key.toString());
        if (lock == null) {
            log.debug("create lock for key : {}", key);
            lock = new ReentrantLock();
            keyLockMap.putIfAbsent(key.toString(), lock);
        }
        try {
            lock.lock();
            value = lookup(key);
            if (value != null) {
                return (T) value;
            }
            // 代表走被拦截的方法逻辑,并返回方法的返回结果
            value = valueLoader.call();
            Object storeValue = toStoreValue(value);
            put(key, storeValue);
            return (T) value;
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e.getCause());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void put(Object key, Object value) {
        // 如果value不能放空，但实际value为空，那么把数据情掉就好。
        if (!super.isAllowNullValues() && value == null) {
            this.evict(key);
            return;
        }
        long expire = getExpire();
        if (expire < 0) {
            expire = -1;
        }

        // 通知其它节点
        //       push(new CacheMessage(this.cacheName, key));
        if (value != null || cacheProperties.isAllowNullValues()) {

            Object cacheValue = toStoreValue(value);
            //            Object redisCacheValue = cacheValue;
            //            if(cacheValue instanceof NullValue){
            //                redisCacheValue = BINARY_NULL_VALUE;
            //            }

            level2Cache.opsForValue().set(getKey(key), cacheValue, expire, TimeUnit.SECONDS);

            level1Cache.put(key, cacheValue);
        }
    }

    /** 使用putIfAbsent方法添加键值对，如果map集合中没有该key对应的值，则直接添加，并返回null，如果已经存在对应的值，则依旧为原来的值。 */
    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        String cacheKey = getKey(key);
        Object prevValue = null;
        // 考虑使用分布式锁，或者将redis的setIfAbsent改为原子性操作
        synchronized (key) {
            prevValue = level2Cache.opsForValue().get(cacheKey);
            if (prevValue == null) {
                long expire = getExpire();
                if (expire > 0) {
                    level2Cache
                            .opsForValue()
                            .set(getKey(key), toStoreValue(value), expire, TimeUnit.SECONDS);
                } else {
                    level2Cache.opsForValue().set(getKey(key), toStoreValue(value));
                }

                //            push(new CacheMessage(this.cacheName, key));

                level1Cache.put(key, toStoreValue(value));
            }
        }
        return toValueWrapper(prevValue);
    }

    @Override
    public void evict(Object key) {
        // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中
        level2Cache.delete(getKey(key));

        push(new CacheMessage(this.cacheName, key, Constants.TWO));

        level1Cache.invalidate(key);
    }

    @Override
    public void clear() {
        // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中
        Set<Object> keys = level2Cache.keys(this.cacheName.concat(":*"));
        for (Object key : keys) {
            level2Cache.delete(key);
        }

        push(new CacheMessage(this.cacheName, null,Constants.TWO));

        level1Cache.invalidateAll();
    }

    /** */
    @Override
    protected Object lookup(Object key) {
        Object value = null;
        // 处理key
        String cacheKey = getKey(key);

        // 从L1获取缓存
        value = level1Cache.getIfPresent(key);
        if (value != null) {
            if (log.isDebugEnabled()) {
                log.debug(
                        "level1Cache get cache, cacheName={}, key={}, value={}",
                        this.getName(),
                        key,
                        value);
            }
            return value;
        }

        // 从L2获取缓存
        value = level2Cache.opsForValue().get(cacheKey);

        if (value != null && log.isDebugEnabled()) {
            log.debug(
                    "level2Cache get cache and put in level1Cache, cacheName={}, key={}, value={}",
                    this.getName(),
                    key,
                    value);
        }
        if (value != null && value instanceof NullValue && cacheProperties.isAllowNullValues()) {
            value = NullValue.INSTANCE;
        }
        // 此处为了服务停机之后重启，l1已经失效但是l2还未失效，不会走put方法，所以在此处设置l1的值
        if (value != null) {
            level1Cache.put(key, toStoreValue(value));
        }

        return value;
    }

    private String getKey(Object key) {
        return this.cacheName.concat(":").concat(key.toString());
    }

    private long getExpire() {
        return expire;
    }

    private void push(CacheMessage message) {
        if (StringUtils.isNotBlank(topic)) {
            message.setTraceId(TraceContext.getTraceId());
            level2Cache.convertAndSend(topic, message);
            log.debug("send clear cache message:{}", message);
        }
    }


    public void clearLocal(Object key) {
        log.debug("clear local cache, the key is : {}", key);
        if (key == null) {
            level1Cache.invalidateAll();
        } else {
            level1Cache.invalidate(key);
        }
    }
}
