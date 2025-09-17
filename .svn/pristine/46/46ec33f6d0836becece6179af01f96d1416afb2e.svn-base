package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  礼品卡联系方式类型
 * @author  wur
 * @date: 2022/12/9 9:06
 **/
@ApiEnum
public enum GiftCardContactType {

    /**
     * 手机
     */
    @ApiEnumProperty("手机")
    MOVE_PHONE,

    /**
     * 固定电话
     */
    @ApiEnumProperty("固定电话")
    FIXED_PHONE,

    /**
     *  微信
     */
    @ApiEnumProperty("微信")
    WECHAT;

    @JsonCreator
    public GiftCardContactType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
