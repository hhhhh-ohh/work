package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum BookingType {


    @ApiEnumProperty("0:全款预售")
    FULL_MONEY,

    @ApiEnumProperty("1:定金预售")
    EARNEST_MONEY;


    @JsonCreator
    public static BookingType fromValue(Integer value) {
        if(value == null) {
            return null;
        }
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
