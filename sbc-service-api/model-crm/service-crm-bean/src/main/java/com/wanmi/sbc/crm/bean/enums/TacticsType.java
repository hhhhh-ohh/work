package com.wanmi.sbc.crm.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum TacticsType {

    @ApiEnumProperty(" 0: 热门推荐")
    HOT,

    @ApiEnumProperty("1：基于商品相关性推荐")
    RELEVANT;


    @JsonCreator
    public TacticsType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
