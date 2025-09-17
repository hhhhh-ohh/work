package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  礼品卡有效期类型
 * @author  wur
 * @date: 2022/12/9 9:06
 **/
@ApiEnum
public enum ExpirationType {

    /**
     * 长期有效
     */
    @ApiEnumProperty("长期有效")
    FOREVER,

    /**
     * 领取多少月内有效
     */
    @ApiEnumProperty("领取多少月内有效")
    MONTH,

    /**
     * 指定具体时间
     */
    @ApiEnumProperty("指定具体时间")
    SPECIFIC_TIME;

    @JsonCreator
    public static ExpirationType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
