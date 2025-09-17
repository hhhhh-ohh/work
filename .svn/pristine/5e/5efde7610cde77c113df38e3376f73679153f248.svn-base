package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * my footmark status
 * Created by zhangrukun on 2022-05-30.
 */
@ApiEnum
public enum FootMarkGoodsStatus {

    @ApiEnumProperty("0: 缺货")
    LACK,
    @ApiEnumProperty("1: 失效")
    OBSOLETE,
    @ApiEnumProperty("2: 正常")
    NORMAL;

    @JsonCreator
    public static FootMarkGoodsStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
