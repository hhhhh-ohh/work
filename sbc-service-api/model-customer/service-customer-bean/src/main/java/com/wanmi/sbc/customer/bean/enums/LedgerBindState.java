package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 分账绑定状态
 */
@ApiEnum
public enum LedgerBindState {

    /**
     * 未绑定
     */
    @ApiEnumProperty("0：未绑定")
    UNBOUND,

    /**
     * 绑定中
     */
    @ApiEnumProperty("1：绑定中")
    CHECKING,

    /**
     * 已绑定
     */
    @ApiEnumProperty("2：已绑定")
    BINDING,

    /**
     * 绑定失败
     */
    @ApiEnumProperty("3：绑定失败")
    BINDING_FAILURE;

    @JsonCreator
    public static LedgerBindState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
