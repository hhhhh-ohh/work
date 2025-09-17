package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 支付状态
 * @author wangchao 2021年03月29日14:36:42
 */
@ApiEnum
public enum CreditPayState {

    @ApiEnumProperty("0: 普通支付")
    PAID,

    @ApiEnumProperty("1: 支付定金")
    DEPOSIT,

    @ApiEnumProperty("2: 支付尾款")
    BALANCE,

    @ApiEnumProperty("3: 定金和尾款")
    ALL;

    @JsonCreator
    public CreditPayState fromValue(int value){
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
