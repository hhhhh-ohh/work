package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author liuli
 *
 */
@ApiEnum
public enum SupplierType {
    @ApiEnumProperty("0:普通商家")
    ORDINARY,

    @ApiEnumProperty("1:跨境商家")
    CROSS_BORDER;

    @JsonCreator
    public static SupplierType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
