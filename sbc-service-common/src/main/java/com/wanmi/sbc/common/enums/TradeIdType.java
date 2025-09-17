package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @ClassName TradeNoType
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/3/16 16:16
 * @Version 1.0
 **/
public enum TradeIdType {

    @ApiEnumProperty(" 9：父订单号")
    PARENT_TRADE_ID(9),

    @ApiEnumProperty(" 1：订单id")
    TRADE_ID(1);

    int value;
    TradeIdType(int value) {
        this.value = value;
    }

    @JsonCreator
    public static TradeIdType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.value;
    }
}
