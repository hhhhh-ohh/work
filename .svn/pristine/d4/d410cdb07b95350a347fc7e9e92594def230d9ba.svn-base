package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author huangzhao 抽奖次数限制类型
 */
@ApiEnum
public enum DrawTimesType {

    @ApiEnumProperty("0：每日")
    PER_DAY,

    @ApiEnumProperty("1：每周")
    PER_WEEK,

    @ApiEnumProperty("2：每月")
    PER_MONTH,

    @ApiEnumProperty("3：每人")
    PER_PERSON;

    @JsonCreator
    public DrawTimesType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
