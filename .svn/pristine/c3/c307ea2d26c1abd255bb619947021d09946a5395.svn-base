package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import lombok.Getter;


/**
 * 社区拼团销售范围
 * @author dyt
 */

@ApiEnum
@Getter
public enum CommunityLeaderRangeType {

    @ApiEnumProperty("0：全部")
    ALL,

    @ApiEnumProperty("1：地区")
    AREA,

    @ApiEnumProperty("2：自定义")
    CUSTOM;

    @JsonCreator
    public CommunityLeaderRangeType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
