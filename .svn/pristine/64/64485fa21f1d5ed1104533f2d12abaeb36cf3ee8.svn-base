package com.wanmi.sbc.empower.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description   数谋平台枚举
 * @author  wur
 * @date: 2022/11/17 9:37
 **/
@ApiEnum
public enum StratagemPlatformType {

    /** OP数谋 */
    @ApiEnumProperty("OP")
    OP;

    @JsonCreator
    public static StratagemPlatformType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
