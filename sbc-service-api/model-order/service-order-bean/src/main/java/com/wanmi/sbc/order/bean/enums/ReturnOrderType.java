package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @Description: 跨境退单列表 退单类型
 * @Since:
 * @Author:
 */
public enum ReturnOrderType {

    @ApiEnumProperty("0: 普通退单")
    GENERAL_TRADE,

    @ApiEnumProperty("1: 跨境退单")
    CROSS_BORDER;

    @JsonCreator
    public ReturnOrderType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
