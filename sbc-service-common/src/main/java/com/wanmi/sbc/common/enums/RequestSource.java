package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum RequestSource {
    @ApiEnumProperty(value = "提货卡")
    PICKUP_CARD;

    @JsonCreator
    public RequestSource fromValue(int i){return values()[i];}

    @JsonValue
    public int toValue(){return this.ordinal();}
}
