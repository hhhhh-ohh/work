package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  礼品卡失效状态 0：已用完  1：已过期 2：已销卡
 * @author  wur
 * @date: 2022/12/9 9:06
 **/
@ApiEnum
public enum GiftCardInvalidStatus {

    /**
     * 已用完
     */
    @ApiEnumProperty("已用完")
    USE_OVER,

    /**
     * 已过期
     */
    @ApiEnumProperty("已过期")
    TIME_OVER,

    /**
     *  已销卡
     */
    @ApiEnumProperty("已销卡")
    CANCELED;

    @JsonCreator
    public GiftCardInvalidStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
