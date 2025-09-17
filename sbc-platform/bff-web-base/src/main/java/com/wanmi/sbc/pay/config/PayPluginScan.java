package com.wanmi.sbc.pay.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhanggaolei
 * @className PluginScan
 * @description
 * @date 2022/11/16 13:42
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//spring中的注解,加载对应的类
@Import(CustomPayRegistryPostProcessor.class)//由这个类来扫描的
@Documented
public @interface PayPluginScan {
    String[] basePackage() default{};
}
