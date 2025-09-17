package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  代销商品运费承担：0.买家 1.卖家
 * @author  wur
 * @date: 2021/9/10 9:28
 **/
@ApiEnum
public enum CommissionFreightBearFlag {
    @ApiEnumProperty("0：买家承担")
    BUYER_BEAR,

    @ApiEnumProperty("1: 卖家承担")
    SELLER_BEAR;
    @JsonCreator
    public CommissionFreightBearFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
