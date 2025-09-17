package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@ApiEnum
public enum CardState {

    /**
     * 未发送
     */
    @ApiEnumProperty("0：未发送")
    NOT_SEND,

    /**
     * 已发送
     */
    @ApiEnumProperty("1: 已发送")
    SEND,

    /**
     * 已失效
     */
    @Schema(description = "2: 已失效")
    INVAILD;

    @JsonCreator
    public CardState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
