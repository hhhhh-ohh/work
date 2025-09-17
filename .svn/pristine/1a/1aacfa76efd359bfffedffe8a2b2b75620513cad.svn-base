package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 筛选支付方式
 * Created by dyt on 2020/6/16.QueryPayType
 */
@ApiEnum
public enum QueryPayType {

    @ApiEnumProperty("支付宝")
    ALIPAY,

    @ApiEnumProperty("微信")
    WECHAT,

    @ApiEnumProperty("企业银联")
    UNIONPAY_B2B,

    @ApiEnumProperty("云闪付")
    UNIONPAY,

    @ApiEnumProperty("余额")
    BALANCE,

    @ApiEnumProperty("授信")
    CREDIT,

    @ApiEnumProperty("线下支付")
    OFFLINE,

    @ApiEnumProperty("积分")
    POINT,

    @ApiEnumProperty("积分+支付宝")
    POINT_ALIPAY,

    @ApiEnumProperty("积分+微信")
    POINT_WECHAT,

    @ApiEnumProperty("积分+企业银联")
    POINT_UNIONPAY_B2B,

    @ApiEnumProperty("积分+云闪付")
    POINT_UNIONPAY,

    @ApiEnumProperty("积分+余额")
    POINT_BALANCE,

    @ApiEnumProperty("积分+授信")
    POINT_CREDIT,

    @ApiEnumProperty("积分+线下支付")
    POINT_OFFLINE;

    @JsonCreator
    public QueryPayType fromValue(Integer value) {
        if(value == null) {
            return null;
        }
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
