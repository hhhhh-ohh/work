package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 备案状态 0：未备案，1：申请备案,2：备案成功，3：已失败
 * @author  wur
 * @date: 2021/6/4 9:10
 **/
public enum CrossGoodsRecordStatus {
    @ApiEnumProperty("0: 待备案")
    NOT_RECORD,

    @ApiEnumProperty("1: 备案中")
    APPLY_RECORD,

    @ApiEnumProperty("2: 备案成功")
    SUCCESS_BORDER,

    @ApiEnumProperty("3: 备案失败")
    ERROR_BORDER;

    @JsonCreator
    public CrossGoodsRecordStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
