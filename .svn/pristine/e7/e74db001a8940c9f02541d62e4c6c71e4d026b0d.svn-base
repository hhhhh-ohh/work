package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import lombok.Getter;

/**
 * 微信商品审核状态
 */
@ApiEnum
@Getter
public enum EditStatus {

    @ApiEnumProperty("1：待审核")
    unchecked(1),
    @ApiEnumProperty("2: 审核中")
    checking(2),
    @ApiEnumProperty("3审核失败")
    failure(3),
    @ApiEnumProperty("4审核成功")
    checked(4);

    private Integer value;

   EditStatus(Integer value) {
        this.value = value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static EditStatus fromValue(Integer value) {
        for (EditStatus editStatus : EditStatus.values()) {
            if (editStatus.value.equals(value)) {
                return editStatus;
            }
        }
        return null;
    }

    @JsonValue
    public int toValue() {
        return this.value;
    }

}
