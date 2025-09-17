package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 代销平台枚举
 * @author wur
 * @date: 2022/4/20 11:01
 */
@ApiEnum
public enum SellPlatformType {

    /** 微信视频号 */
    @ApiEnumProperty("WECHAT_VIDEO")
    WECHAT_VIDEO,

    /** 非代销 */
    @ApiEnumProperty("NOT_SELL")
    NOT_SELL,

    /** 小程序订阅消息 */
    @ApiEnumProperty("MINI_PROGRAM_SUBSCRIBE")
    MINI_PROGRAM_SUBSCRIBE,

    /** 小程序支付物流 */
    @ApiEnumProperty("MINI_PROGRAM_PAY")
    MINI_PROGRAM_PAY,

    /** 京东VOP */
    @ApiEnumProperty("京东VOP")
    VOP;

    @JsonCreator
    public static SellPlatformType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}

