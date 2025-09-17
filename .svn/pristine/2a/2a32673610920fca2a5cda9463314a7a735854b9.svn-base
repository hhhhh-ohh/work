package com.wanmi.sbc.setting.bean.enums;/**
 * @author 黄昭
 * @create 2021/9/10 10:36
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @className WriteOffStatus
 * @description 核销状态
 * @author 黄昭
 * @date 2021/9/10 10:36
 **/
@ApiEnum
public enum  WriteOffStatus {

    /**
     * 未核销
     */
    @ApiEnumProperty("0:未核销")
    NOT_WRITTEN_OFF,

    /**
     * 已核销
     */
    @ApiEnumProperty("1:已核销")
    WRITTEN_OFF;

    @JsonCreator
    public WriteOffStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
