package com.wanmi.sbc.pay.config;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * @author zhanggaolei
 * @className FindPayPluginServiceClassPathScanHandle
 * @description
 * @date 2022/11/16 16:01
 **/
public class FindPayPluginServiceClassPathScanHandle extends ClassPathBeanDefinitionScanner {

    public FindPayPluginServiceClassPathScanHandle(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        //添加过滤条件，这里是只添加了@MarektingPluginServer的注解才会被扫描到
        addIncludeFilter(new AnnotationTypeFilter(PayPluginService.class));
        //调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        return beanDefinitionHolders;
    }
}
