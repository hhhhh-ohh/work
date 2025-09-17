package com.wanmi.sbc.common.plugin.annotation;

import com.wanmi.sbc.common.plugin.handler.RoutingConfigurationSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用插件路由
 * @author zhengyang
 * @className EnablePlugin
 * @date 2021/6/24 15:03
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RoutingConfigurationSelector.class})
public @interface EnablePluginRouting {
}
