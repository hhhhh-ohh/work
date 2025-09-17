package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author huangzhao
 */

@ApiEnum
public enum DrawPrizeType {
    @ApiEnumProperty("0：积分")
    POINTS,

    @ApiEnumProperty("1：优惠券")
    COUPON,

    @ApiEnumProperty("2：实物奖品")
    GOODS,

    @ApiEnumProperty("3：自定义")
    CUSTOMIZE;

    @JsonCreator
    public static DrawPrizeType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
