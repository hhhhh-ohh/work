package com.wanmi.sbc.common.plugin.handler;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 需要导入Spring管理的类全限定名
 * @author zhengyang
 * @className RouteConfigurationSelector
 * @date 2021/6/24 15:05
 */
public class RoutingConfigurationSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {};
    }
}
