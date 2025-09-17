package com.wanmi.sbc.crm.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @Author lvzhenwei
 * @Description 商品管理--禁推标识
 * @Date 14:00 2020/11/18
 * @Param
 * @return
 **/
@ApiEnum
public enum NoPushType {

    @ApiEnumProperty(" 0: 关闭禁推")
    CLOSED,

    @ApiEnumProperty("1：开启禁推")
    OPEN;


    @JsonCreator
    public NoPushType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
