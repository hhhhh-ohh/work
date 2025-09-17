package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import lombok.Getter;


/**
 * 汇总发货单类型
 * @author dyt
 */

@ApiEnum
@Getter
public enum DeliveryOrderAreaSummaryType {

    @ApiEnumProperty("0：团长")
    PROVINCE,

    @ApiEnumProperty("1：区域")
    CITY,

    @ApiEnumProperty("2：自定义")
    CUSTOM;

    @JsonCreator
    public DeliveryOrderAreaSummaryType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
