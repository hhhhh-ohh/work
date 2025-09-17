package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 卡密发送状态
 * @author 许云鹏
 */
@ApiEnum
public enum CardSendState {

    /**
     * 发送成功
     */
    @ApiEnumProperty("0:发送成功")
    SUCCESS,

    /**
     * 发送中
     */
    @ApiEnumProperty("1:发送中")
    SENDING,

    /**
     * 发送失败
     */
    @ApiEnumProperty("2:发送失败")
    FAIL;


    @JsonCreator
    public static CardSendState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
