package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 礼品卡详情状态 0：未兑换 1：未激活 2：已激活 3：已销卡 4：已过期
 * @author malianfeng
 * @date 2022/12/9 14:21
 */
public enum GiftCardDetailStatus {

    /**
     * 未兑换
     */
    @ApiEnumProperty("0：未兑换")
    NOT_EXCHANGE,

    /**
     * 未激活
     */
    @ApiEnumProperty("1：未激活")
    NOT_ACTIVE,

    /**
     * 已激活
     */
    @ApiEnumProperty("2：已激活")
    ACTIVATED,

    /**
     * 已销卡
     */
    @ApiEnumProperty("3：已销卡")
    CANCELED,

    /**
     * 已过期
     */
    @ApiEnumProperty("4：已过期")
    EXPIRED;

    @JsonCreator
    public static GiftCardDetailStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
