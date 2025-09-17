package com.wanmi.ares.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author zhaiqiankun
 * @className VideoTimeType
 * @description 统计类型,0:视频号报表,1:店铺报表
 * @date 2022/4/7 19:45
 **/
@ApiEnum
public enum StatisticsType {
    @ApiEnumProperty("0:视频号报表")
    ACCOUNT("视频号报表"),
    @ApiEnumProperty("1:店铺报表")
    SHOP("店铺报表"),
    @ApiEnumProperty("2:天报表")
    DAYTIME("天报表"),
    @ApiEnumProperty("3:概况")
    TOTAL("概况");

    StatisticsType(String desc) {
        this.desc = desc;
    }

    /**
     * 描述信息
     */
    private String desc;

    @JsonCreator
    public StatisticsType fromValue(int value) {
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
