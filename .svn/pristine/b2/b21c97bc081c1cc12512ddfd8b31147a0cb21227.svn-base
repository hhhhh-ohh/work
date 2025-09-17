package com.wanmi.sbc.marketing.newplugin.config;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;

import java.util.Set;

/**
 * @author zhanggaolei
 * @className CustomRegistryPostProcessor
 * @description
 * @date 2021/5/26 13:39
 **/
public class CustomMarketingRegistryPostProcessor implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    ResourceLoader resourceLoader;

    public CustomMarketingRegistryPostProcessor(){

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        //获取所有注解的属性和值
        AnnotationAttributes annotationAttributes =
                AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(MarketingPluginScan.class.getName()));
        //获取到basePackage的值
        String[] basePackages = annotationAttributes.getStringArray("basePackage");
        //如果没有设置basePackage 扫描路径,就扫描对应包下面的值
        if(basePackages.length == 0){
            basePackages = new String[]{((StandardAnnotationMetadata) annotationMetadata).getIntrospectedClass().getPackage().getName()};
        }

        //自定义的包扫描器
        FindMarketingPluginServiceClassPathScanHandle scanHandle = new FindMarketingPluginServiceClassPathScanHandle(registry,false);

        if(resourceLoader != null){
            scanHandle.setResourceLoader(resourceLoader);
        }
        //这里实现的是根据名称来注入
        scanHandle.setBeanNameGenerator(new MarketingPluginBeanNameGenerator());
        //扫描指定路径下的接口
        Set<BeanDefinitionHolder> beanDefinitionHolders = scanHandle.doScan(basePackages);
    }
}
