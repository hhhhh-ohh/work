package com.wanmi.sbc.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @description 图片list验证
 * @author daiyitian
 * @date 2021/4/16 15:35
 */
@Component
public class IsImageListValidator implements ConstraintValidator<IsImageList, List<String>> {
    private List<String> acceptExtNames = Arrays.asList("jpg", "JPG","jpeg", "JPEG","png","PNG","gif");
    private int length = 255;

    @Override
    public void initialize(IsImageList constraintAnnotation) {
        length = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        // 允许为空
        if (CollectionUtils.isEmpty(values)) {
            return true;
        }
        for (String img : values) {
            if (img.length() > this.length) {
                return false;
            }

            // 校验是否为网络地址
            if (!img.startsWith("http://") && !img.startsWith("https://")) {
                return false;
            }
            if (!img.contains(".")) {
                return false;
            }
            String extName = img.substring(img.lastIndexOf(".") + 1);
            if (!acceptExtNames.contains(extName)) {
                return false;
            }
        }
        return true;
    }
}
