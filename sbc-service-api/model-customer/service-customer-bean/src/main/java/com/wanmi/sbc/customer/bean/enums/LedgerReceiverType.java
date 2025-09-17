package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 分账接收方类型
 */
@ApiEnum
public enum LedgerReceiverType {

    /**
     * 平台
     */
    @ApiEnumProperty("0：平台")
    PLATFORM,

    /**
     * 供应商
     */
    @ApiEnumProperty("1：供应商")
    PROVIDER,

    /**
     * 分销员
     */
    @ApiEnumProperty("2：分销员")
    DISTRIBUTION;

    @JsonCreator
    public static LedgerReceiverType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
