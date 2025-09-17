package com.wanmi.sbc.message.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 商家消息一级类型
 * @author malianfeng
 * @date 2022/7/6 21:02
 */
@ApiEnum
public enum StoreMessageType {

    @ApiEnumProperty(" 0:消息")
    MESSAGE,

    @ApiEnumProperty(" 1:公告")
    NOTICE;

    @JsonCreator
    public static StoreMessageType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
