package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 优惠券活动业务来源 0:sbc系统产生,1:对外接入产生
 *
 * @author daiyitian
 */
@ApiEnum
public enum CouponActivitySource {

    /** 0:sbc系统产生 */
    @ApiEnumProperty("0:sbc系统产生")
    SBC,

    /** 1:外部接入产生 */
    @ApiEnumProperty("1:外部接入产生")
    OPEN;

    @JsonCreator
    public static CouponActivitySource fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
