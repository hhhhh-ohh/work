package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum(dataType = "java.lang.String")
public enum BuyType {
    @ApiEnumProperty("立即购买")
    IMMEDIATE_BUY("immediateBuy"),
    @ApiEnumProperty("开店礼包")
    STORE_BAGS_BUY("storeBagsBuy"),
    @ApiEnumProperty("组合购")
    SUIT_BUY("suitBuy"),
    @ApiEnumProperty("拼团购买")
    GROUP_BUY("groupBuy"),
    @ApiEnumProperty("购物车下单")
    CONFIRM_BUY("confirmBuy"),
    @ApiEnumProperty("砍价商品下单")
    BARGAIN("bargainBuy"),
    @ApiEnumProperty("周期购商品下单")
    CYCLE_BUY("cycleBuy"),
    @ApiEnumProperty("快速下单")
    QUICK_ORDER_BUY("quickOrderBuy"),
    @ApiEnumProperty(value = "提货卡下单")
    PICKUP_CARD_BUY("pickupCardBuy"),
    @ApiEnumProperty(value = "社区团购下单")
    COMMUNITY_BUY("communityBuy");

    private String buyType;

    BuyType(String buyType){
        this.buyType = buyType;
    }

    public String getBuyType() {
        return buyType;
    }

    @JsonValue
    public String toValue(){
        return getBuyType();
    }
}
