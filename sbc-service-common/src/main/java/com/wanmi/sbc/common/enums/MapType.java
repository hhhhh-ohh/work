package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @className MapType
 * @description 地图类型
 * @author 张文昌
 * @date 2021/7/15 10:37
 */
@ApiEnum(dataType = "java.lang.String")
public enum MapType {

    /** 高德地图 */
    @ApiEnumProperty("0：高德地图")
    GAO_DE("GaoDeService");

    private String desc;

    MapType(String desc) {
        this.desc = desc;
    }

    @JsonCreator
    public static MapType fromValue(int value) {
        return values()[value];
    }

    public String getDesc() {
        return desc;
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
