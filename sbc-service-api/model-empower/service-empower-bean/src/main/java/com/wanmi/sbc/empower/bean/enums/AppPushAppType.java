package com.wanmi.sbc.empower.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 友盟推送平台类型
 */
@ApiEnum
public enum AppPushAppType {
    @ApiEnumProperty(" 0:iOS平台")
    IOS,
    @ApiEnumProperty(" 1:android平台")
    ANDROID;

    @JsonCreator
    public static AppPushAppType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
