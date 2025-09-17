package com.wanmi.sbc.common.configure.log;


import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zhanggaolei
 * @className RequestLogRegistryPostProcessor
 * @description
 * @date 2021/5/26 13:39
 **/
public class RequestLogRegistryPostProcessor implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private RequestLogProperties requestLogProperties;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        if (requestLogProperties!=null && requestLogProperties.isEnable()) {
            BeanDefinitionBuilder beanDefinitionBuilder =
                    BeanDefinitionBuilder.rootBeanDefinition(CustomFilterRegistrationBean.class)
                            // 增加构造参数
                            .addConstructorArgValue(requestLogProperties);
            // 注册一个名字的 bean
            registry.registerBeanDefinition(
                    "requestLogFilter", beanDefinitionBuilder.getBeanDefinition());
            registry.registerBeanDefinition(
                    "requestLogAspect", new RootBeanDefinition(RequestLogAspect.class));
        }

    }

    @Override
    public void setEnvironment(Environment environment) {
        BindResult<RequestLogProperties> bindResult = Binder.get(environment).bind("request.log", RequestLogProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            requestLogProperties = bindResult.get();
        }
    }


}

