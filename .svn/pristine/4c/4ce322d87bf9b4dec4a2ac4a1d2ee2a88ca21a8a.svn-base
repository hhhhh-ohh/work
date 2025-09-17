package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  交易类型 0：订单抵扣 1：订单退款 2: 礼品卡激活（发卡礼品卡） 3：礼品卡激活（兑换礼品卡） 4：销卡
 * @author  wur
 * @date: 2022/12/9 9:06
 **/
@ApiEnum
public enum GiftCardBusinessType {

    /**
     * 订单抵扣
     */
    @ApiEnumProperty("订单抵扣")
    ORDER_DEDUCTION,

    /**
     * 订单退款
     */
    @ApiEnumProperty("订单退款")
    ORDER_REFUND,

    /**
     * 礼品卡激活（发卡礼品卡）
     */
    @ApiEnumProperty("礼品卡激活（发卡礼品卡）")
    ACTIVATE_CARD_FOR_SEND_CARD,

    /**
     * 礼品卡激活（兑换礼品卡）
     */
    @ApiEnumProperty("礼品卡激活（兑换礼品卡）")
    ACTIVATE_CARD_FOR_EXCHANGE_CARD,

    /**
     * 消卡
     */
    @ApiEnumProperty("消卡")
    CANCEL_CARD,

    /**
     * 订单取消  下单失败取消、用户手动取消、超时未支付取消
     */
    @ApiEnumProperty("订单取消")
    ORDER_CANCEL;

    @JsonCreator
    public GiftCardBusinessType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
