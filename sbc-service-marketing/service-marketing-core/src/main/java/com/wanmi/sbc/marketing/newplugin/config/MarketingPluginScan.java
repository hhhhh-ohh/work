package com.wanmi.sbc.marketing.newplugin.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhanggaolei
 * @className PluginScan
 * @description
 * @date 2021/5/28 16:41
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//spring中的注解,加载对应的类
@Import(CustomMarketingRegistryPostProcessor.class)//由这个类来扫描的
@Documented
public @interface MarketingPluginScan {
    String[] basePackage() default{};
}
