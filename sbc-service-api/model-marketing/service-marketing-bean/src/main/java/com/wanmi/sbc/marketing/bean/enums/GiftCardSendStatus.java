package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 礼品卡发送状态 0：待发 1：成功 2：失败
 * @author malianfeng
 * @date 2022/12/9 14:21
 */
public enum GiftCardSendStatus {

    /**
     * 待发
     */
    @ApiEnumProperty("0：待发")
    WAITING_SEND,

    /**
     * 未激活
     */
    @ApiEnumProperty("1：成功")
    SUCCEEDED,

    /**
     * 已激活
     */
    @ApiEnumProperty("2：失败")
    FAILED;

    @JsonCreator
    public GiftCardSendStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
