package com.wanmi.sbc.common.util;

import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * @Author: songhanlin
 * @Date: Created In 14:26 2022/2/16
 * @Description: TODO
 */
public class FileResourceUtil {

    public static Resource getPackageInsideResourcesByPattern(String resourceName) throws IOException {
        String resourcePathPattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resourceName;
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(resourcePathPattern);
        return resources[resources.length - 1];
    }

    private static final BeanExpressionResolver resolver = new StandardBeanExpressionResolver();

    /**
     * 解析一个表达式，获取一个值
     * @param beanFactory
     * @param value 一个固定值或一个表达式。如果是一个固定值，则直接返回固定值，否则解析一个表达式，返回解析后的值
     * @return
     */
    public static Object resolveExpression(ConfigurableBeanFactory beanFactory, String value) {
        String resolvedValue = beanFactory.resolveEmbeddedValue(value);

        if (!(resolvedValue.startsWith("#{") && value.endsWith("}"))) {
            return resolvedValue;
        }

        return resolver.evaluate(resolvedValue, new BeanExpressionContext(beanFactory, null));
    }

}
