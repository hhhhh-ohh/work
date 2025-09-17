package com.wanmi.sbc.empower.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import lombok.Getter;

/**
 * APP消息推送类型枚举类
 **/
@ApiEnum
@Getter
public enum AppPushPlatformType {

    @ApiEnumProperty(" 0：友盟推送")
    UMENG(AppPushPlatformType.BEAN_NAME_UMENG);

    public static final String BEAN_NAME_UMENG = "appPushUmengService";

    private String beanName;

    AppPushPlatformType(String beanName) {
        this.beanName = beanName;
    }

    @JsonCreator
    public static AppPushPlatformType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
