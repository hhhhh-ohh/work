package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  代销商品基本信息同步 0.手动同步 1.自动同步
 * @author  wur
 * @date: 2021/9/10 9:28
 **/
@ApiEnum
public enum CommissionInfoSynFlag {
    @ApiEnumProperty("0：手动同步")
    HAND_SYN,

    @ApiEnumProperty("1: 自动同步")
    AI_SYN;
    @JsonCreator
    public CommissionInfoSynFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
