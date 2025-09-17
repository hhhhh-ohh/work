package com.wanmi.sbc.vas.bean.enums.recommen;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年
 **/
@ApiEnum
public enum StatisticalRangeType {

    @ApiEnumProperty(" 0: 近一个月")
    ONE_MONTH,

    @ApiEnumProperty("1：近三个月")
    THREE_MONTHS,

    @ApiEnumProperty("2：近六个月")
    SIX_MONTHS,

    @ApiEnumProperty("3：近一年")
    ONE_YEAR;

    @JsonCreator
    public StatisticalRangeType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
