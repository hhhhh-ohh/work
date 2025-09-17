package com.wanmi.sbc.message.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 商家公告目标范围枚举
 * @author malianfeng
 * @date 2022/7/6 21:02
 */
@ApiEnum
public enum StoreNoticeTargetScope {

    @ApiEnumProperty(" 0:全部")
    ALL,

    @ApiEnumProperty(" 1:自定义")
    CUSTOMIZE;

    @JsonCreator
    public static StoreNoticeTargetScope fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
