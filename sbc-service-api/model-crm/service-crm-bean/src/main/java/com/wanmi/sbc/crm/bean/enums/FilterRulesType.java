package com.wanmi.sbc.crm.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 过滤规则类型：0：展示后去重，1：点击后去重，2：购买后去重
 **/
@ApiEnum
public enum FilterRulesType {

    @ApiEnumProperty(" 0: 关闭禁推")
    SHOWED,

    @ApiEnumProperty("1：开启禁推")
    CLICKED,

    @ApiEnumProperty("1：开启禁推")
    PURCHASED;

    @JsonCreator
    public FilterRulesType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
