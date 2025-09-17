package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author huangzhao 抽奖类型
 */
@ApiEnum
public enum DrawType {

    @ApiEnumProperty("0：无限制")
    UNLIMITED,

    @ApiEnumProperty("1：积分")
    POINTS;

    @JsonCreator
    public DrawType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
