package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 礼品卡兑换方式 0：卡密模式
 * @author malianfeng
 * @date 2022/12/9 14:21
 */
public enum GiftCardExchangeMode {

    /**
     * 卡密模式
     */
    @ApiEnumProperty("0：卡密模式")
    SECRET_KEY;

    @JsonCreator
    public GiftCardExchangeMode fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
