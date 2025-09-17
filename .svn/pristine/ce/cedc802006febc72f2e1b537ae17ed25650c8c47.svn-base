package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  代销设价类型 0：智能设价 1：手动设价
 * @author  wur
 * @date: 2021/9/10 9:28
 **/
@ApiEnum
public enum CommissionSynPriceType {
    @ApiEnumProperty("0：智能设价")
    AI_SYN,

    @ApiEnumProperty("1: 手动设价")
    HAND_SYN;
    @JsonCreator
    public CommissionSynPriceType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
