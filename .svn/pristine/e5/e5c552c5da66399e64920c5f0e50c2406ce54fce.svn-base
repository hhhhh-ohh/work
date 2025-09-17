package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author lvzhenwei
 * @className OrderDeductionType
 * @description 订单抵扣方式枚举
 * @date 2022/12/12 3:41 下午
 **/
@ApiEnum
public enum OrderDeductionType {

    @ApiEnumProperty("积分")
    POINT("积分"),

    @ApiEnumProperty("礼品卡-现金卡")
    CASH_GIFT_CARD("礼品卡-现金卡"),
    @ApiEnumProperty("礼品卡-提货卡")
    PICKUP_GIFT_CARD("礼品卡-提货卡");


    private String desc;

    OrderDeductionType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static OrderDeductionType fromValue(Integer value) {
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
