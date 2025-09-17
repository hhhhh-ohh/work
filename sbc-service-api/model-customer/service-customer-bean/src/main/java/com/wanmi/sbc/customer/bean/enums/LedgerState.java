package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 分账开通审核状态
 */
@ApiEnum
public enum LedgerState {

    /**
     * 未开通
     */
    @ApiEnumProperty("0：未开通")
    NOT_CHECK,

    /**
     * 审核中
     */
    @ApiEnumProperty("1：审核中")
    CHECKING,

    /**
     * 已审核
     */
    @ApiEnumProperty("2：已审核")
    PASS,

    /**
     * 审核失败
     */
    @ApiEnumProperty("2：审核失败")
    NOT_PASS;

    @JsonCreator
    public static LedgerState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
