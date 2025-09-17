package com.wanmi.sbc.crm.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema
public enum TriggerCondition {
    @ApiEnumProperty("0:无")
    NONE,
    @ApiEnumProperty("1：有访问")
    VISIT,
    @ApiEnumProperty("2：有收藏")
    FOLLOW,
    @ApiEnumProperty("3：有加购")
    ADD,
    @ApiEnumProperty("4：有下单")
    BUY,
    @ApiEnumProperty("5：有付款")
    PAY,
    @ApiEnumProperty("6：有申请退单")
    RETURN_ORDER,
    @ApiEnumProperty("7：有评价商品")
    GOODS_EVALUATE,
    @ApiEnumProperty("8：有评价店铺")
    STORE_EVALUATE,
    @ApiEnumProperty("9：有关注店铺")
    FOLLOW_STORE,
    @ApiEnumProperty("10：有分享商品")
    SHARE_GOODS,
    @ApiEnumProperty("11：有分享商城")
    SHARE_S2B,
    @ApiEnumProperty("12：有分享店铺")
    SHARE_STORE,
    @ApiEnumProperty("13：有分享赚")
    COMMISSION,
    @ApiEnumProperty("14：有邀请好友")
    INVITE,
    @ApiEnumProperty("15：有签到")
    SIGN;


    @JsonCreator
    public TriggerCondition fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

    public static TriggerCondition chgValue(int value) {
        return values()[value];
    }
}
