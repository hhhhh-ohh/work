package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后
 * 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团
 * 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时
 */
@ApiEnum
public enum TriggerNodeType {
    @ApiEnumProperty(" 0：商家发货")
    ORDER_DELIVERY,

    @ApiEnumProperty(" 1：自动确认收货前24小时")
    AUTO_CONFIRM_RECEIPT,

    @ApiEnumProperty(" 2：售后申请商家审核通过或失败后")
    AFTER_SALES_APPLICATION,

    @ApiEnumProperty(" 3：商家/供应商发送退货地址后")
    RETURN_NOTICE,

    @ApiEnumProperty(" 4：退款回调通知成功")
    REFUND_SUCCESS,

    @ApiEnumProperty(" 5：自动发券至用户账户时")
    COUPON_ISSUANCE,

    @ApiEnumProperty(" 6：优惠券过期前24小时")
    COUPON_USAGE,

    @ApiEnumProperty(" 7：距离拼团结束还剩3小时，且未成团")
    BEFORE_GROUPON_END,

    @ApiEnumProperty(" 8：拼团成功")
    GROUPON_SUCCESS,

    @ApiEnumProperty(" 9：拼团失败")
    GROUPON_FAIL,

    @ApiEnumProperty(" 10：尾款开始支付")
    BALANCE_PAYMENT,

    @ApiEnumProperty(" 11：距离尾款结束支付还有3小时")
    BALANCE_PAYMENT_OVERTIME,

    @ApiEnumProperty(" 12：付费会员距离过期前24小时")
    MEMBER_EXPIRATION,

    @ApiEnumProperty(" 13：新活动通知")
    NEW_ACTIVITY;

    @JsonCreator
    public static TriggerNodeType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
