package com.wanmi.sbc.crm.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;


@ApiEnum
public enum PositionType {

    @ApiEnumProperty(" 0: 购物车")
    SHOP_CART,

    @ApiEnumProperty("1：商品详情")
    GOODS_DETAIL,

    @ApiEnumProperty(" 2: 商品列表")
    GOODS_LIST,

    @ApiEnumProperty("3：个人中心")
    USER_CENTER,

    @ApiEnumProperty("4：会员中心")
    CUSTOMER_CENTER,

    @ApiEnumProperty(" 5: 收藏商品")
    COLLECT_GOODS,

    @ApiEnumProperty("6：支付成功页")
    PAY_SUC,

    @ApiEnumProperty(" 7: 分类")
    GOODS_CATE,

    @ApiEnumProperty(" 8: 魔方")
    MAGIC_BOX;


    @JsonCreator
    public PositionType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
