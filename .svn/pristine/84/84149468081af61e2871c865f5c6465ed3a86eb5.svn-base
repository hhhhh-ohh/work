package com.wanmi.sbc.common.sensitiveword.annotation;

import com.wanmi.sbc.common.enums.SignWordType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @description   返回数据敏感字处理
 * @author  wur
 * @date: 2022/7/5 10:16
 **/
@Retention(RUNTIME)
@Target(FIELD)
@Documented
public @interface SensitiveWordsField {

    SignWordType signType() ;
}