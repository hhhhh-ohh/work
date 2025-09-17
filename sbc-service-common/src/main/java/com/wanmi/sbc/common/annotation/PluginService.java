package com.wanmi.sbc.common.annotation;

import com.wanmi.sbc.common.enums.PluginType;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author wur
 * @className PluginService
 * @description 插件服务注解
 * @date 2021/5/24 10:23
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface PluginService {

    String value() default "";

    /***
     * 被注解的插件类型
     * @return
     */
    PluginType type() default PluginType.CROSS_BORDER;
}
