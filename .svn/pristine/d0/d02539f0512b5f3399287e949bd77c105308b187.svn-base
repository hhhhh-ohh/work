package com.wanmi.sbc.account.bean.enums;

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

    @ApiEnumProperty("礼品卡")
    GIFT_CARD("礼品卡");

    private String desc;

    OrderDeductionType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static OrderDeductionType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
