package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 小程序类型枚举
 *
 * @author daiyitian
 */
public enum QrCodeType {

    @ApiEnumProperty("0：普通二维码")
    QR_NORMAL,

    @ApiEnumProperty("1：小程序码")
    QR_MINI_PROGRAM;

    @JsonCreator
    public static QrCodeType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public String toValue() {
        return String.valueOf(this.ordinal());
    }
}
