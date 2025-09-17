package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 审核状态 0：待审核 1：已审核 2：审核失败 3：禁用中
 * Created by Daiyitian on 2017/4/13.
 */
@ApiEnum
public enum LeaderCheckStatus {
    @ApiEnumProperty("0：待审核")
    WAIT_CHECK,

    @ApiEnumProperty("1：已审核")
    CHECKED,

    @ApiEnumProperty("2：审核失败")
    NOT_PASS,

    @ApiEnumProperty("3：禁用中")
    FORBADE;

    @JsonCreator
    public LeaderCheckStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
