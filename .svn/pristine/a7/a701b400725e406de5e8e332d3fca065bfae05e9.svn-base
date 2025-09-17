package com.wanmi.sbc.message.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 商家公告发送状态枚举
 * @author malianfeng
 * @date 2022/7/6 21:02
 */
@ApiEnum
public enum StoreNoticeSendStatus {

    @ApiEnumProperty("0:未发送")
    NOT_SENT,

    @ApiEnumProperty("1:发送中")
    SENDING,

    @ApiEnumProperty("2:已发送")
    SENT,

    @ApiEnumProperty("3:发送失败")
    SEND_FAIL,

    @ApiEnumProperty("4:已撤回")
    WITHDRAW;

    @JsonCreator
    public static StoreNoticeSendStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
