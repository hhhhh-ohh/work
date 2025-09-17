package com.wanmi.sbc.vas.bean.enums.recommen;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum PositionOpenFlag {

    @ApiEnumProperty(" 0: 关闭")
    CLOSED,

    @ApiEnumProperty("1：开启")
    OPEN;


    @JsonCreator
    public static PositionOpenFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
