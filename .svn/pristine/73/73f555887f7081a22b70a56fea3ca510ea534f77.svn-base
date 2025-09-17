package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

public enum UseType {

    /**
     * 定时任务
     */
    @ApiEnumProperty("0、定时任务")
    JOB,

    /**
     * 分账
     */
    @ApiEnumProperty("1、分账")
    LEDGER;

    @JsonCreator
    public static UseType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
