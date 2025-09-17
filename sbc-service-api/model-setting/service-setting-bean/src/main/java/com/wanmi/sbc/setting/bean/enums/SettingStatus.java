package com.wanmi.sbc.setting.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 视频带货应用接入设置状态
 * @author malianfeng
 * @date 2022/4/18 15:54
 */
@ApiEnum
public enum SettingStatus {

    @ApiEnumProperty("0:待开通")
    DISABLE,
    @ApiEnumProperty("1:已启用")
    ENABLE,
    @ApiEnumProperty("2:禁用")
    FORBID,
    @ApiEnumProperty("3:开通中")
    OPENING;

    @JsonCreator
    public static SettingStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
