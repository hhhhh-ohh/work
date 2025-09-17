package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum DeliveryCycleType {

    @ApiEnumProperty("1:每天一期")
    DAY,

    @ApiEnumProperty("2:每周一期")
    WEEK_ONE,

    @ApiEnumProperty("3:每月一期")
    MONTH_ONE,

    @ApiEnumProperty("4:每周固定多期")
    WEEK_MANY,

    @ApiEnumProperty(("5:每月固定多期"))
    MONTH_MANY,

    @ApiEnumProperty(("6:每周自选多期"))
    WEEK_OPT_MANY,

    @ApiEnumProperty(("7:每月自选多期"))
    MONTH_OPT_MANY;

    @JsonCreator
    public static DeliveryCycleType fromValue(int value) {
        if (value < 1) {
            return null;
        }
        return values()[value - 1];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal() + 1;
    }
}
