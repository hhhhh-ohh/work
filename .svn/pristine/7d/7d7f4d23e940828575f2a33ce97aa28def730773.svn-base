package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  礼品卡有效期类型
 * @author  wur
 * @date: 2022/12/9 9:06
 **/
@ApiEnum
public enum GiftCardScopeType {

    /**
     * 所有
     */
    @ApiEnumProperty("所有")
    ALL,

    /**
     * 品牌
     */
    @ApiEnumProperty("品牌")
    BRAND,

    /**
     * 类目
     */
    @ApiEnumProperty("类目")
    CATE,

    /**
     * 店铺
     */
    @ApiEnumProperty("店铺")
    STORE,

    /**
     * 商品
     */
    @ApiEnumProperty("商品")
    GOODS;

    @JsonCreator
    public GiftCardScopeType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
