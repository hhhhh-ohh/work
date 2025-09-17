package com.wanmi.sbc.common.config.redis;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author zhanggaolei
 * @className RedisCondition
 * @description TODO
 * @date 2021/6/30 18:44
 **/
public class RedisCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String host = context.getEnvironment().getProperty("spring.data.redis.host");
        String clusterNodes = context.getEnvironment().getProperty("spring.data.redis.cluster.nodes");
        String sentinel = context.getEnvironment().getProperty("spring.data.redis.sentinel.master");
        if(host==null && clusterNodes==null && sentinel==null){
            return false;
        }
        return true;
    }
}
