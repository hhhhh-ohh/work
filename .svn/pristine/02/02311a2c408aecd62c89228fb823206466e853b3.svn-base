package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 礼品卡批次类型 0：制卡 1：发卡
 * @author malianfeng
 * @date 2022/12/9 14:21
 */
public enum GiftCardBatchType {

    /**
     * 制卡
     */
    @ApiEnumProperty("0：制卡")
    MAKE,

    /**
     * 发卡
     */
    @ApiEnumProperty("1：发卡")
    SEND;

    @JsonCreator
    public GiftCardBatchType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
