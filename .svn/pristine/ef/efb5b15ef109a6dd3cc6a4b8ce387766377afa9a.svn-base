package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 清分账户审核状态
 */
@ApiEnum
public enum LedgerAccountState {

    /**
     * 未进件
     */
    @ApiEnumProperty("0：未进件")
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
    public static LedgerAccountState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
