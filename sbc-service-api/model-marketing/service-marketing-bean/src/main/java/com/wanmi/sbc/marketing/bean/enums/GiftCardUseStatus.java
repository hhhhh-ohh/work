package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  礼品卡使用状态 0：可用  1：不可用 2：待激活
 * @author  wur
 * @date: 2022/12/9 9:06
 **/
@ApiEnum
public enum GiftCardUseStatus {

    /**
     * 可用
     */
    @ApiEnumProperty("可用")
    USE,

    /**
     * 不可用
     */
    @ApiEnumProperty("不可用")
    INVALID,

    /**
     *  待激活
     */
    @ApiEnumProperty("待激活")
    NOT_ACTIVE;

    @JsonCreator
    public GiftCardUseStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
