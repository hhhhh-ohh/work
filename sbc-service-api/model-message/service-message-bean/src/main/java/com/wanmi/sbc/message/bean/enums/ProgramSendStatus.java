package com.wanmi.sbc.message.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 推送状态 0：未推送，1：推送中，2：已推送，3：推送失败，4：部分失败
 */
@ApiEnum
public enum ProgramSendStatus {
    @ApiEnumProperty(" 0：未推送")
    NOT_SEND,

    @ApiEnumProperty(" 1：推送中")
    SENDING,

    @ApiEnumProperty(" 2：已推送")
    SEND_DOWN,

    @ApiEnumProperty(" 3：推送失败")
    SEND_FAIL,

    @ApiEnumProperty(" 4：部分失败")
    SEND_PART,

    @ApiEnumProperty(" 5：ALL")
    ALL;

    @JsonCreator
    public static ProgramSendStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
