package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 资源服务类型 0：待审核 1：已审核 2：审核失败 3：禁售中
 * Created by Daiyitian on 2017/4/13.
 */
@ApiEnum
public enum CommunityResourceServiceType {
    @ApiEnumProperty("0：团长自提点")
    LEADER_PICKUP_POINT,

    @ApiEnumProperty("1：拼团活动")
    ACTIVITY;

    @JsonCreator
    public CommunityResourceServiceType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
