package com.wanmi.sbc.common.annotation;

import com.wanmi.sbc.common.enums.ThirdPlatformType;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author EDZ
 * @className ThirdService
 * @description 三方服务注解
 * @date 2021/5/24 10:23
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface ThirdService {

    String value() default "";

    /***
     * 被注解的三方平台类型
     * @return
     */
    ThirdPlatformType type() default ThirdPlatformType.VOP;
}
