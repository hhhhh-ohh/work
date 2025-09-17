package com.wanmi.sbc.message.test;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheMessage;
import com.wanmi.sbc.common.cache.CaffeineCacheUtils;
import com.wanmi.sbc.common.cache.MultiCacheManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanggaolei
 * @className TestController
 * @description
 * @date 2022/5/7 18:43
 **/
@Slf4j
@RestController
public class TestCacheController {

    @Autowired
    UserCacheService userCacheService;

//    @Autowired
    private CacheManager wmCaffeineCache;

    @Autowired
    private RedisTemplate<Object,Object> stringKeyRedisTemplate;

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    public BaseResponse getUserById(@RequestBody String userId){
        return BaseResponse.success(userCacheService.queryUser(userId));
    }

    @RequestMapping(value = "/getRedis",method = RequestMethod.POST)
    public BaseResponse getUserByIdRedis(@RequestBody String userId){
        return BaseResponse.success(userCacheService.queryUserForRedis(userId));
    }

    @RequestMapping(value = "/getMulti",method = RequestMethod.POST)
    public BaseResponse getUserByIdMulti(@RequestBody String userId){
        return BaseResponse.success(userCacheService.queryUserForMulti(userId));
    }
    @RequestMapping(value = "/put",method = RequestMethod.POST)
    public BaseResponse put(@RequestBody UserDTO userDTO){
        return BaseResponse.success(userCacheService.putUser(userDTO.userId,userDTO));
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public BaseResponse query(@RequestBody UserDTO userDTO){
        getCacheValue("cacheDefault:","cache_user_id_user01");
        return BaseResponse.SUCCESSFUL();
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody UserDTO userDTO){
        userCacheService.evictUserSync("cache_user_id_user01");
        return BaseResponse.SUCCESSFUL();
    }

    @RequestMapping(value = "/pub",method = RequestMethod.POST)
    public BaseResponse publish(@RequestBody PublishCacheMessage cacheMessage){
        stringKeyRedisTemplate.convertAndSend(cacheMessage.topic, cacheMessage);
        return BaseResponse.SUCCESSFUL();
    }

    public Object getCacheValue(String cacheName, Object key) {

        if (null == wmCaffeineCache.getCache(cacheName)) {
            return null;
        }

        if (null == wmCaffeineCache.getCache(cacheName).get(key)) {
            return null;
        }

        return wmCaffeineCache.getCache(cacheName).get(key).get();
    }

    @Data
    static class PublishCacheMessage extends CacheMessage{
        private String topic;
    }
}

