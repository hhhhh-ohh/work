package com.wanmi.sbc.order.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @Description: 跨境订单清关状态
 * @Since: 2019-05-27 19:08
 * @Author: liutao
 */
public enum DeclaredStatus {

    @ApiEnumProperty("0: 待报关")
    WAIT_DECLARE,

    @ApiEnumProperty("1: 清关中")
    IN_DECLARE,

    @ApiEnumProperty("2: 已清关")
    SUCCESS_DECLARE,

    @ApiEnumProperty("3: 清关失败")
    FAIL_DECLARE;

    @JsonCreator
    public DeclaredStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
