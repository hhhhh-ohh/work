package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 分账商户类型
 */
@ApiEnum
public enum LedgerAccountType {

    /**
     * 商户
     */
    @ApiEnumProperty("0：商户")
    MERCHANTS,

    /**
     * 接收方
     */
    @ApiEnumProperty("1：接收方")
    RECEIVER;

    @JsonCreator
    public static LedgerAccountType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
