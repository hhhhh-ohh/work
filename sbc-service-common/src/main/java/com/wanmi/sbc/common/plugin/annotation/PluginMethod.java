package com.wanmi.sbc.common.plugin.annotation;

import java.lang.annotation.*;

/**
 * @author zhanggaolei
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PluginMethod {

    /**
     * 类和方法的路径，不填默认是当前方法的路径
     */
    String value() default "";

    /**
     * 是不是主入口
     */
    boolean main() default false;

    /**
     * 排序，默认为1，值越小越靠前
     */
    int order() default 1;

    /**
     * 是否替换，默认为false，如果设置为true，则不执行其他后续方法/切面，
     */
    boolean replace() default false;
}
