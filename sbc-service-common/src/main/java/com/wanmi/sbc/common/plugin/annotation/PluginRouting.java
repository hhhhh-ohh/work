package com.wanmi.sbc.common.plugin.annotation;

import com.wanmi.sbc.common.plugin.enums.PluginRoutingRule;

import java.lang.annotation.*;

/**
 * 标明一个Service支持路由注入
 * @author zhengyang
 * @className PluginRouting
 * @date 2021/6/24 15:18
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PluginRouting {

    /***
     * 类路由规则，默认方法级过滤
     * @return
     */
    PluginRoutingRule routingRule() default PluginRoutingRule.METHOD;
}
