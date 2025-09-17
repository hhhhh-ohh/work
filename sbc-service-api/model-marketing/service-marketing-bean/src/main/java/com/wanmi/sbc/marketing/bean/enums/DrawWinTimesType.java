package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author huangzhao
 */
@ApiEnum
public enum DrawWinTimesType {

    @ApiEnumProperty("0：无限制")
    UNLIMITED,

    @ApiEnumProperty("1：每人每天")
    PER_PERSON_PER_DAY,

    @ApiEnumProperty("2：每人每次")
    PER_PERSON_PER_FREQUENCY;

    @JsonCreator
    public DrawWinTimesType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
