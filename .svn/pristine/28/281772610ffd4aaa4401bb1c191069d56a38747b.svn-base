package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 卡密发放失败原因
 * @author 许云鹏
 */
@ApiEnum
public enum CardFailReason {

    /**
     * 库存不足
     */
    @ApiEnumProperty("0:库存不足")
    NO_STOCK,

    /**
     * 已过销售期
     */
    @ApiEnumProperty("1:已过销售期")
    OVERDUE,

    /**
     * 其他原因
     */
    @ApiEnumProperty("2:其他原因")
    OTHER;


    @JsonCreator
    public static CardFailReason fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
