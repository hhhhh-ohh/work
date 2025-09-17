package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @Description: 抽奖活动查询类型，0：全部，1：进行中，2：暂停中，3：未开始，4：已结束
 * @author: qiyong
 * @create: 2021/4/13 16:50
 */
@ApiEnum(dataType = "java.lang.Integer")
public enum DrawActivityStatus {

    @ApiEnumProperty("0：全部")
    ALL,

    @ApiEnumProperty("1：进行中")
    STARTED,

    @ApiEnumProperty("2：暂停中")
    PAUSED,

    @ApiEnumProperty("3：未开始")
    NOT_START,

    @ApiEnumProperty("4：已结束")
    ENDED,

    @ApiEnumProperty("5：不包括已结束的所有")
    NOT_ENDED;

    @JsonCreator
    public DrawActivityStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
