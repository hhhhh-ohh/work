package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

public enum BargainActivityState {

    @ApiEnumProperty("0：未开始")
    WAIT_ACTIVITY,

    @ApiEnumProperty("1：活动已结束")
    TERMINATED_ACTIVITY,

    @ApiEnumProperty("2：活动进行中")
    ONGOING_ACTIVITY;

    @JsonCreator
    public static BargainActivityState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public String toValue() {
        return String.valueOf(this.ordinal());
    }
}
