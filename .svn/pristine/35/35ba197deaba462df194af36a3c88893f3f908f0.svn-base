package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 不可售原因
 * @author  shy
 * @date 2022/8/30 10:10
 **/
@ApiEnum
public enum VendibilityReason {
    /**
     * 不可售(已删除)
     */
    @ApiEnumProperty("0:不可售(已删除)")
    DELETED,

    /**
     * 不可售(已下架)
     */
    @ApiEnumProperty("不可售(已下架)")
    REMOVE,

    /**
     * 不可售（已关店）
     */
    @ApiEnumProperty("不可售(已关店)")
    CLOSED,

    /**
     * 不可售（已过期）
     */
    @ApiEnumProperty("不可售(已过期)")
    EXPIRED;


    @JsonCreator
    public static VendibilityReason fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public Integer toValue() {
        return this.ordinal();
    }
}
