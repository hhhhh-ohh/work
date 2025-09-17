package com.wanmi.sbc.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 图片格式校验
 */
@Component
public class IsImageValidator implements ConstraintValidator<IsImage, String> {
    private Set<String> acceptExtNames = new HashSet<>(Arrays.asList("jpg","jpeg","png","gif", "JPG", "JPEG", "PNG", "GIF"));
    @Override
    public void initialize(IsImage constraintAnnotation) {
        // 验证器初始化方法
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        //允许为空
        if(StringUtils.isBlank(value)) {
            return true;
        }
        //校验是否为网络地址
        if(!value.startsWith("http://") && !value.startsWith("https://")) {
            return false;
        }
        if(!value.contains(".")) {
            return false;
        }
        String extName = value.substring(value.lastIndexOf('.')+1);
        if (Objects.nonNull(extName)){
            extName = extName.toLowerCase();
        }
        if (!acceptExtNames.contains(extName)) {
            return false;
        }
        return true;
    }
}
