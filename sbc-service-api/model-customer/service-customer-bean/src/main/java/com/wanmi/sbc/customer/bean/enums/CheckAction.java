package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 审核行为枚举
 * @author malianfeng
 * @date 2022/4/19 18:24
 */
@ApiEnum
public enum CheckAction {

    @ApiEnumProperty("1：审核")
    CHECKED(1),
    @ApiEnumProperty("2：驳回")
    NOT_PASS(2),
    @ApiEnumProperty("3：禁用")
    DISABLE(3);
    private Integer value;

    CheckAction(Integer value) {
        this.value = value;
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static CheckAction fromValue(int value) {
        for (CheckAction checkAction : values()) {
            if (checkAction.value == value) {
                return checkAction;
            }
        }
        return null;
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

}
