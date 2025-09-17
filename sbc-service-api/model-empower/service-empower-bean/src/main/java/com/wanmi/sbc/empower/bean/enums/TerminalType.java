package com.wanmi.sbc.empower.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 终端: 0 pc 1 h5 2 app
 * Created by sunkun on 2017/8/3.
 */
@ApiEnum
public enum TerminalType {

    @ApiEnumProperty("PC")
    PC,

    @ApiEnumProperty("H5")
    H5,

    @ApiEnumProperty("APP")
    APP,

    @ApiEnumProperty("微信内H5")
    WX_H5,

    @ApiEnumProperty("小程序")
    MINI;

    @JsonCreator
    public TerminalType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
