package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 分账错误处理状态
 */
@ApiEnum
public enum LedgerErrorState {

    /**
     * 未处理
     */
    @ApiEnumProperty("0：未处理")
    UNDO,

    /**
     * 处理中
     */
    @ApiEnumProperty("1：处理中")
    DOING,

    /**
     * 处理成功
     */
    @ApiEnumProperty("2：处理成功")
    SUCCESS,

    /**
     * 绑定失败
     */
    @ApiEnumProperty("3：处理失败")
    FAIL;

    @JsonCreator
    public static LedgerErrorState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
