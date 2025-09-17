package com.wanmi.sbc.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author sunkun
 * @date 2018/3/20
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeCheck {

    String fieldName() default "employeeIds";

    /**
     * 填充业务员相关的会员id
     * 为空则不填充
     */
    String customerIdField() default "";

    /**
     * 填充业务员相关的会员DetailId
     * 为空则不填充
     */
    String customerDetailIdField() default "";
}
