package com.wanmi.sbc.empower.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import lombok.Getter;

/**
 * 客服平台类型枚举类
 **/
@ApiEnum
@Getter
public enum CustomerServicePlatformType {

    @Deprecated
    @ApiEnumProperty(" 0：QQ客服")
    QQ,

    @ApiEnumProperty(" 1：阿里云云客服")
    ALIYUN,

    @ApiEnumProperty(" 2：企微客服")
    WECHAT,

    @ApiEnumProperty(" 3：网易七鱼客服")
    QIYU;

    @JsonCreator
    public static CustomerServicePlatformType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
