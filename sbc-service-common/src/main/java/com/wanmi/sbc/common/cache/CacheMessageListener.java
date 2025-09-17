package com.wanmi.sbc.common.cache;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.configure.ApplicationContextConfigure;
import com.wanmi.sbc.common.util.SpringContextHolder;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.tools.logtrace.core.constant.TraceConstants;
import com.wanmi.tools.logtrace.core.context.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author zhanggaolei
 * @className CacheMessageListener
 * @description
 * @date 2022/5/9 14:30
 */
@Slf4j
public class CacheMessageListener implements MessageListener {

//    private MultiCacheManager multiCacheManager;
    private RedisTemplate<Object, Object> redisTemplate;


    public CacheMessageListener(
            RedisTemplate<Object, Object> redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String json = new String(message.getBody());
        CacheMessage cacheMessage = JSONObject.parseObject(json, CacheMessage.class);
        if(cacheMessage == null|| StringUtils.isBlank(cacheMessage.getCacheName())){
            log.warn("cacheMessage is null or cacheName is null");
            return;
        }
        // 设置链路追踪id
        TraceContext.putTraceId(cacheMessage.getTraceId());
        MDC.put(TraceConstants.KEY_TRACE_ID, TraceContext.getTraceId());
        MDC.put(TraceConstants.KEY_SPAN_ID, TraceContext.getSpanId());
        log.debug(
                "recevice a redis topic message, clear {} cache, the cacheName is {}, the key is {}",
                cacheMessage.getBean(),
                cacheMessage.getCacheName(),
                cacheMessage.getKey());

      //  multiCacheManager.clearLocal(cacheMessage.getCacheName(), cacheMessage.getKey());
        if(ApplicationContextConfigure.CONTEXT.containsBean(cacheMessage.getBean())){
            CacheManager cacheManager = (CacheManager) ApplicationContextConfigure.CONTEXT.getBean(cacheMessage.getBean());
            if(cacheMessage.getBean().equals(CacheType.MULTI)){
                MultiCacheManager multiCacheManager = (MultiCacheManager) cacheManager;
                multiCacheManager.clearLocal(cacheMessage.getCacheName(),cacheMessage.getKey());
            } else {
                if (cacheMessage.getKey() == null) {
                    cacheManager.getCache(cacheMessage.getCacheName()).clear();
                } else {
                    cacheManager.getCache(cacheMessage.getCacheName()).evict(cacheMessage.getKey());
                }
            }
        }

    }
}
