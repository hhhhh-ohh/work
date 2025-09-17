package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  礼品卡状态 0：待激活  1：已激活 2：已销卡
 * @author  wur
 * @date: 2022/12/9 9:06
 **/
@ApiEnum
public enum GiftCardStatus {

    /**
     * 待激活
     */
    @ApiEnumProperty("待激活")
    NOT_ACTIVE,

    /**
     * 已激活
     */
    @ApiEnumProperty("已激活")
    ACTIVATED,

    /**
     *  已销卡
     */
    @ApiEnumProperty("已销卡")
    CANCELED;

    @JsonCreator
    public GiftCardStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
