package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 周期购配送状态
 */
@ApiEnum
public enum CycleDeliveryState {

    /**
     * 待配送
     */
    @ApiEnumProperty("0: 待配送")
    NOT_SHIP,

    /**
     * 已配送
     */
    @ApiEnumProperty("1: 已配送")
    SHIPPED,

    /**
     * 已顺延
     */
    @ApiEnumProperty("2: 已顺延")
    POSTPONED,

    @ApiEnumProperty("3: 已改期")
    UPDATE_SEN_TIME;

    @JsonCreator
    public CycleDeliveryState fromValue(int value){
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
