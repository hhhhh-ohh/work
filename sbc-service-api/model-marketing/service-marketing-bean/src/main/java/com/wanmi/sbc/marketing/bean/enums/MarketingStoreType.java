package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 参与店铺是：0全部，1指定店铺
 */
@ApiEnum
public enum MarketingStoreType {
    /**
     * 全部
     */
    @ApiEnumProperty("0：全部")
    STORE_TYPE_ALL(0),

    /**
     * 指定店铺
     */
    @ApiEnumProperty("1：指定店铺")
    STORE_TYPE_APPOINT(1);

    private int type;

    MarketingStoreType(int type){
        this.type = type;
    }

    @JsonCreator
    public MarketingStoreType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.type;
    }
}
