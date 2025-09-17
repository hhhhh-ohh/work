package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 商品购买方式
 * @author  daiyitian
 * @date 2021/4/9 14:58
 **/
@ApiEnum
public enum GoodsBuyType {
    /**
     * 购物车
     */
    @ApiEnumProperty("购物车")
    CART,

    /**
     * 立即购买
     */
    @ApiEnumProperty("立即购买")
    IMMEDIATE;

    @JsonCreator
    public static GoodsBuyType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public Integer toValue() {
        return this.ordinal();
    }
}
