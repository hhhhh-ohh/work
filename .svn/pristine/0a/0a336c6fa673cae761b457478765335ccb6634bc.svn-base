package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @Author: wur
 * @Date: 2022/8/25 10:38
 */
@ApiEnum
public enum GoodsEditFlag {
    @ApiEnumProperty("0: 上架")
    UP,
    @ApiEnumProperty("1: 下架")
    DOWN,
    @ApiEnumProperty("2: 删除")
    DELETE,
    @ApiEnumProperty("3: 禁售")
    FORBID,
    @ApiEnumProperty("4: 基础信息")
    INFO;
    @JsonCreator
    public GoodsEditFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
