package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 商品类型 0商品 1商品库
 *
 * @author CHENLI
 */
@ApiEnum
public enum GoodsPropertyType {
    /** 商品 */
    @ApiEnumProperty("0：商品")
    GOODS,
    /** 商品库商品 */
    @ApiEnumProperty("1: 商品库商品")
    STANDARD_GOODS;

    @JsonCreator
    public GoodsPropertyType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
