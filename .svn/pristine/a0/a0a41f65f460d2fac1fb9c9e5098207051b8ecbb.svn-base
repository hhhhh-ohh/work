package com.wanmi.sbc.vas.bean.enums.recommen;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum TacticsType {

    @ApiEnumProperty(" 0: 热门推荐")
    HOT,

    @ApiEnumProperty("1：基于商品相关性推荐")
    RELEVANT,

    @ApiEnumProperty("2：基于用户兴趣推荐")
    INTEREST;

    @JsonCreator
    public static TacticsType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
