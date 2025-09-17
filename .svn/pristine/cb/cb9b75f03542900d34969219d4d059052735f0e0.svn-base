package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum PayingLevelState {

    /**
     * 生效中
     */
    @ApiEnumProperty("0: 生效中")
    ACTIVE,

    /**
     * 未生效
     */
    @ApiEnumProperty("1: 未生效")
    NOT_ACTIVE,

    /**
     * 已过期
     */
    @ApiEnumProperty("2: 已过期")
    EXPIRED,

    /**
     * 已退款
     */
    @ApiEnumProperty("3: 已退款")
    REFUND;

    @JsonCreator
    public PayingLevelState fromValue(int value){
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
