package com.wanmi.sbc.marketing.newplugin.config;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * @author zhanggaolei
 * @className FindPluginServiceClassPathScanHandle
 * @description
 * @date 2021/5/28 16:01
 **/
public class FindMarketingPluginServiceClassPathScanHandle extends ClassPathBeanDefinitionScanner {

    public FindMarketingPluginServiceClassPathScanHandle(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        //添加过滤条件，这里是只添加了@MarektingPluginServer的注解才会被扫描到
        addIncludeFilter(new AnnotationTypeFilter(MarketingPluginService.class));
        //调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        return beanDefinitionHolders;
    }
}
