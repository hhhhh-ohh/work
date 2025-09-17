package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author 黄昭
 * @className AuditType
 * @description 审核类型 1:初次审核 2:二次审核
 * @date 2021/12/17 10:05
 **/
@ApiEnum
public enum AuditType {

    @ApiEnumProperty("0：初次审核")
    INITIAL_AUDIT,

    @ApiEnumProperty("1：二次审核")
    SECOND_AUDIT;

    @JsonCreator
    public AuditType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }


}