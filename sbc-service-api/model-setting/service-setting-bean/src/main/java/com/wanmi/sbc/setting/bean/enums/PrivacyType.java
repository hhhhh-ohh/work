package com.wanmi.sbc.setting.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 隐私政策类型
 * Created by 王超 on 2023/5/31.
 */
@ApiEnum
public enum PrivacyType {
    @ApiEnumProperty("0:APP")
    APP,
    @ApiEnumProperty("1:小程序")
    MINI;

    @JsonCreator
    public static PrivacyType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
