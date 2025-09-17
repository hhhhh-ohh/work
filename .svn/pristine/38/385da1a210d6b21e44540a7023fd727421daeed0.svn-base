package com.wanmi.sbc.common.sensitiveword.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @description   返回数据敏感字处理
 * @author  wur
 * @date: 2022/7/5 10:16
 **/
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Documented
public @interface ReturnSensitiveWords {

    String functionName();
}