package com.wanmi.sbc.account.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author houshuai
 */

@ApiEnum
public enum CreditAuditStatus {

    /**
     * 待审核
     */
    @ApiEnumProperty("待审核")
    WAIT,

    /**
     * 拒绝
     */
    @ApiEnumProperty("拒绝")
    REJECT,

    /**
     * 通过
     */
    @ApiEnumProperty("通过")
    PASS,

    /**
     * 变更额度审核
     */
    @ApiEnumProperty("变更额度待审核")
    RESET_WAIT;

    @JsonCreator
    public static CreditAuditStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}