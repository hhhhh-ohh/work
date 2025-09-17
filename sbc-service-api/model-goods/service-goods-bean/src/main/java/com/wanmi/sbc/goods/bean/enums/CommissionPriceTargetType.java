package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  代销加价目标类型 ：0.类目 1.SKU
 * @author  wur
 * @date: 2021/9/10 9:28
 **/
@ApiEnum
public enum CommissionPriceTargetType {
    @ApiEnumProperty("0：类目")
    CATE,

    @ApiEnumProperty("1: SKU")
    SKU;
    @JsonCreator
    public CommissionPriceTargetType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
