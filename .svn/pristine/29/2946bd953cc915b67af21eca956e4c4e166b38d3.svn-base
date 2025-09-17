package com.wanmi.sbc.pay.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * @author zhanggaolei
 * @className PayPluginBeanNameGenerator
 * @description
 * @date 2022/11/16 16:06
 **/
public class PayPluginBeanNameGenerator extends AnnotationBeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        //从自定义注解中拿name
        String name = getNameByServiceFindAnnotation(definition,registry);
        if(name != null && !"".equals(name)){
            return name;
        }
        //走父类的方法
        return super.generateBeanName(definition, registry);
    }

    private String getNameByServiceFindAnnotation(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanClassName = definition.getBeanClassName();
        try {
            Class<?> aClass = Class.forName(beanClassName);
            PayPluginService annotation = aClass.getAnnotation(PayPluginService.class);
            if(annotation == null){
                return null;
            }
            //获取到注解name的值并返回
            return annotation.type().getPayService();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
