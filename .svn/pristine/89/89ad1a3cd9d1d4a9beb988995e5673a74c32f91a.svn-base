package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum MarketingCustomerType {

    /**
     * 普通会员
     */
    @ApiEnumProperty("0 普通会员")
    ORDINARY,

    /**
     * 付费会员
     */
    @ApiEnumProperty("1 付费会员")
    PAYING_MEMBER;

    @JsonCreator
    public MarketingCustomerType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
