package com.wanmi.sbc.marketing.bean.enums;

import com.alibaba.fastjson2.annotation.JSONCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum GiftCardType {
    @ApiEnumProperty("0:现金卡")
    CASH_CARD,
    @ApiEnumProperty("1:提货卡")
    PICKUP_CARD;

    @JSONCreator
    public GiftCardType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}

