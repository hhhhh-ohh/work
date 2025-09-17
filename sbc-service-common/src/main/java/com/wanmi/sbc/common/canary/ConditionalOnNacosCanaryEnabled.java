package com.wanmi.sbc.common.canary;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhanggaolei
 * @className ConditionOnNacosCanaryEnabled
 * @description
 * @date 2021/6/4 9:49
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@ConditionalOnProperty(value = "nacos.ribbon.canary.enabled",
        matchIfMissing = false)
public @interface ConditionalOnNacosCanaryEnabled {
}
