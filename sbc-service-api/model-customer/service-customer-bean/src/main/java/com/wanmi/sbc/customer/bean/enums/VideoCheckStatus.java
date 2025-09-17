package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author zhaiqiankun
 * @className CheckStatus
 * @description 审核状态
 * @date 2022/4/12 18:02
 **/
@ApiEnum
public enum VideoCheckStatus {
    @ApiEnumProperty("0：待审核")
    WAIT_CHECK(0),
    @ApiEnumProperty("1：已审核")
    CHECKED(1),
    @ApiEnumProperty("2：审核未通过")
    NOT_PASS(2),
    @ApiEnumProperty("3：禁用")
    DISABLE(3);
    private Integer value;

    VideoCheckStatus(Integer value) {
        this.value = value;
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static VideoCheckStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

}
