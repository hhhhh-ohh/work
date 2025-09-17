package com.wanmi.sbc.common.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum NewAdd {
    @ApiEnumProperty("0:否")
    NO,
    @ApiEnumProperty("1:是")
    YES;

    @JsonCreator
    public static NewAdd fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
