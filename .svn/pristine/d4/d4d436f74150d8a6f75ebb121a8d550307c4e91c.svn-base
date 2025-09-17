package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 授权节点类型
 */
@ApiEnum
public enum ProgramNodeType {
    @ApiEnumProperty(" 0：订单支付成功")
    ORDER_PAY_SUCCESS,

    @ApiEnumProperty(" 1：退单提交成功")
    REFUND_ORDER_SUCCESS,

    @ApiEnumProperty(" 2：查看我的优惠券")
    VIEW_COUPON,

    @ApiEnumProperty(" 3：参与/发起拼团成功")
    GROUPON_SUCCESS,

    @ApiEnumProperty(" 4：商品预约成功")
    APPOINTMENT_SUCCESS,

    @ApiEnumProperty(" 5：付费会员购买成功")
    MEMBER_PAY_SUCCESS;

    @JsonCreator
    public static ProgramNodeType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
