package com.wanmi.sbc.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsImageListValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsImageList {
    String message() default "图片地址非法";

    /**
     * 字符长度
     */
    int length() default 255;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
