package com.wanmi.sbc.marketing.bean.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author huangzhao 抽奖形式
 */
@ApiEnum
public enum DrawFromType {
    @ApiEnumProperty("0：九宫格")
    NINE_GRIDS,

    @ApiEnumProperty("1：大转盘")
    BIG_TURNTABLE;

    @JsonCreator
    public DrawFromType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
