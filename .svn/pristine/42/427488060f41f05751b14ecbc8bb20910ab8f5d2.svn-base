package com.wanmi.sbc.common.plugin.annotation;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.plugin.enums.MethodRoutingRule;

import java.lang.annotation.*;

/**
 * 方法级路由
 * @author zhengyang
 * @className Routing
 * @date 2021/6/24 15:22
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Routing {

    /***
     * 方法路由规则，默认替换
     * @return
     */
    MethodRoutingRule routingRule() default MethodRoutingRule.REPLACE;

    /***
     * 路由El表达式
     * @return
     */
    String el() default "";

    /***
     * MethodRoutingRule等于PLUGIN_TYPE时生效，路由的PluginType
     * @return
     */
    PluginType pluginType() default PluginType.NORMAL;
}
