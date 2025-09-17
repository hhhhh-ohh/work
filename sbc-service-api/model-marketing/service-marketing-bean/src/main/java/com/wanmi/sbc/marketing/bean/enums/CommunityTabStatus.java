package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 查询类型，0:进行中    1:已结束   2:未开始
 */
@ApiEnum
public enum CommunityTabStatus {


    @ApiEnumProperty("0：进行中")
    STARTED,

    @ApiEnumProperty("1：已结束")
    ENDED,

    @ApiEnumProperty("2：未开始")
    NOT_START;

    @JsonCreator
    public CommunityTabStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
