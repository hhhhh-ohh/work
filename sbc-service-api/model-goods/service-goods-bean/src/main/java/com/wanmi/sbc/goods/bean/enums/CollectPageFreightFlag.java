package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 运费凑单页标识 0：即将开场，1：进行中，2：已结束
 */
@ApiEnum
public enum CollectPageFreightFlag {

    @ApiEnumProperty("0: 免运费")
    FREE,

    @ApiEnumProperty("1：可凑单")
    COLLECT,

    @ApiEnumProperty("2：已超出")
    OUT;

    @JsonCreator
    public CollectPageFreightFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
