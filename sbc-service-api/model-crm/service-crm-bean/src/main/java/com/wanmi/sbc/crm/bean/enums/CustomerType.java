package com.wanmi.sbc.crm.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum CustomerType {

    @ApiEnumProperty(value = "普通会员")
    CUSTOMER,

    @ApiEnumProperty(value = "企业会员")
    ENTERPRISE_CUSTOMER;

    @JsonCreator
    public static CustomerType fromValue(int name){return values()[name];}

    @JsonValue
    public int fromValue(){return this.ordinal();}

}
