package com.wanmi.sbc.common.annotation;

import java.lang.annotation.*;

/**
 * @Author: songhanlin
 * @Date: Created In 16:04 2022/2/16
 * @Description: 万米资源注解
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WmResource {

    /**
     * 文件名称, 需确保在resources目录下
     **/
    String value();

}
