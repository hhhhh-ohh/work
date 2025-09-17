package com.wanmi.sbc.crm.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum CustomerIdentity {

    @ApiEnumProperty(value = "普通会员")
    CUSTOMER,

    @ApiEnumProperty(value = "分销员")
    DISTRIBUTION;

    @JsonCreator
    public static CustomerIdentity fromValue(int name) {
        return values()[name];
    }

    @JsonValue
    public int c() {
        return this.ordinal();
    }
}
