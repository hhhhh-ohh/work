package com.wanmi.sbc.message.test;

import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.cache.CacheType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className UserCacheService
 * @description
 * @date 2022/5/7 19:25
 **/
@Slf4j
@Service
public class UserCacheService {

    /**
     * 用于模拟db
     */
    private static Map<String, UserDTO> userMap = new HashMap<>();

    {
        userMap.put("user01", new UserDTO("1", "张三"));
        userMap.put("user02", new UserDTO("2", "李四"));
        userMap.put("user03", new UserDTO("3", "王五"));
        userMap.put("user04", new UserDTO("4", "赵六"));
    }

    /**
     * 获取或加载缓存项
     */
    @Cacheable(key = "'cache_user_id_' + #userId", value = CacheConstants.GLOBAL_CACHE_NAME)
    public UserDTO queryUser(String userId) {
        UserDTO userDTO = userMap.get(userId);
        try {
            Thread.sleep(1000);// 模拟加载数据的耗时
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        log.info("加载数据:{}", userDTO);
        return userDTO;
    }

    /**
     * 获取或加载缓存项
     */
    @Cacheable(key = "'cache_user_id_' + #userId",  value = CacheConstants.GLOBAL_CACHE_NAME,cacheManager = CacheType.REDIS)
    public UserDTO queryUserForRedis(String userId) {
        UserDTO userDTO = userMap.get(userId);
        try {
            Thread.sleep(1000);// 模拟加载数据的耗时
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        log.info("加载数据:{}", userDTO);
        return userDTO;
    }


    /**
     * 获取或加载缓存项
     */
    @Cacheable(key = "'cache_user_id_' + #userId", value = CacheConstants.GLOBAL_CACHE_NAME /*value = "test"*/,cacheManager = CacheType.MULTI)
    public UserDTO queryUserForMulti(String userId) {
        UserDTO userDTO = userMap.get(userId);
        try {
            Thread.sleep(1000);// 模拟加载数据的耗时
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        log.info("加载数据:{}", userDTO);
        return userDTO;
    }

    /**
     * 获取或加载缓存项
     * <p>
     * 注：因底层是基于caffeine来实现一级缓存，所以利用的caffeine本身的同步机制来实现
     * sync=true 则表示并发场景下同步加载缓存项，
     * sync=true，是通过get(Object key, Callable<T> valueLoader)来获取或加载缓存项，此时valueLoader（加载缓存项的具体逻辑）会被缓存起来，所以CaffeineCache在定时刷新过期缓存时，缓存项过期则会重新加载。
     * sync=false，是通过get(Object key)来获取缓存项，由于没有valueLoader（加载缓存项的具体逻辑），所以CaffeineCache在定时刷新过期缓存时，缓存项过期则会被淘汰。
     * <p>
     */
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "#userId", sync = true,cacheManager = CacheType.MULTI)
    public List<UserDTO> queryUserSyncList(String userId) {
        UserDTO userDTO = userMap.get(userId);
        List<UserDTO> list = new ArrayList();
        list.add(userDTO);
        log.info("加载数据:{}", list);
        return list;
    }

    /**
     * 更新缓存
     */
    @CachePut(value = "userCache", key = "#userId")
    public UserDTO putUser(String userId, UserDTO userDTO) {
        return userDTO;
    }

    /**
     * 淘汰缓存
     */
    @CacheEvict(value = CacheConstants.GLOBAL_CACHE_NAME,cacheManager = CacheType.MULTI, key = "#userId")
    public String evictUserSync(String userId) {
        return userId;
    }

}
