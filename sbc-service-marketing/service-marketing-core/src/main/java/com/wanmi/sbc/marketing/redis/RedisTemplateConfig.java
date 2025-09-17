package com.wanmi.sbc.marketing.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.List;
import java.util.Map;

@Configuration
public class RedisTemplateConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public DefaultRedisScript<String> marketingRedisScript() {
        DefaultRedisScript<String> marketingRedisScript = new DefaultRedisScript<>();
        marketingRedisScript.setResultType(String.class);
        marketingRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("goods_marketing_cache.lua")));
        return marketingRedisScript;
    }

}
