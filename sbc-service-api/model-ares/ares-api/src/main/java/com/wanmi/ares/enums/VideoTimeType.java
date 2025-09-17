package com.wanmi.ares.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author zhaiqiankun
 * @className VideoTimeType
 * @description 查询报表时间类型
 * @date 2022/4/7 19:45
 **/
@ApiEnum
public enum VideoTimeType {
    @ApiEnumProperty("0:当天")
    DAY("当天"),
    @ApiEnumProperty("1:最近7天")
    WEEK("最近7天"),
    @ApiEnumProperty("2:最近30天")
    THIRD("最近30天"),
    @ApiEnumProperty("3:自然月")
    MONTH("自然月");

    /**
     * 描述信息
     */
    private String desc;

    VideoTimeType(String desc) {
        this.desc = desc;
    }

    @JsonCreator
    public VideoTimeType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

    @Override
    public String toString() {
        return String.valueOf(toValue());
    }

    public String getDesc() {
        return desc;
    }

}
