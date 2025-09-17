package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * h5/app优惠券魔方页面，优惠券数据状态查询
 */
@ApiEnum
public enum MagicCouponStatus {

    @ApiEnumProperty("0：可领取（当前账号未领取）")
    AVAILABLE,

    @ApiEnumProperty("1：查看可用（已领取但券还未生效）")
    NOT_YET_EFFECTIVE,

    @ApiEnumProperty("2：立即使用（已领取且券已生效）")
    USE,

    @ApiEnumProperty("3：已抢光（券的上限已达到）")
    EMPTY,

    @ApiEnumProperty("4：活动未开始")
    NOT_START,

    @ApiEnumProperty("5：活动已结束")
    ALREADY_END
    ;

    @JsonCreator
    public MagicCouponStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
