package com.wanmi.sbc.configuration.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** JWT认证自动配置类 Created by aqlu on 15/11/20. */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(prefix = "jwt", name = "secret-key")
@Order(Ordered.LOWEST_PRECEDENCE - 100) // 优先级空100个位置出来, 满足业务在JWT认证之后添加拦截器处理
public class JwtAutoConfiguration implements WebMvcConfigurer {

    @Autowired private JwtProperties jwtProperties;
    @Autowired private RedisTemplate<String, ?> redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        String secretKey = jwtProperties.getSecretKey();
        String jwtHeaderKey = jwtProperties.getJwtHeaderKey();
        String jwtHeaderPrefix = jwtProperties.getJwtHeaderPrefix();
        String excludedRestUrls = jwtProperties.getExcludedRestUrls();
        Boolean excludedRedis = jwtProperties.getExcludedRedis();

        registry.addWebRequestInterceptor(
                        new JwtInterceptor(
                                secretKey,
                                jwtHeaderKey,
                                jwtHeaderPrefix,
                                excludedRestUrls,
                                redisTemplate,
                                excludedRedis))
                .addPathPatterns(jwtProperties.getUrlPatterns())
                .excludePathPatterns(jwtProperties.getExcludedUrls());
    }
}
