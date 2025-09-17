package com.wanmi.sbc.crm.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * @ClassName GroupType
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/2/18 15:03
 * @Version 1.0
 **/
@Schema
public enum GroupType {

    @ApiEnumProperty("自定义")
    CUSTOM,

    @ApiEnumProperty("生命周期")
    LIFE_CYCLE;

    @JsonCreator
    public static GroupType fromValue(int name) {
        return values()[name];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
